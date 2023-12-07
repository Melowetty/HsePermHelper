package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonDto
import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.LessonType
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleFileConverter
import org.apache.poi.ss.usermodel.*
import org.springframework.stereotype.Component
import java.io.InputStream
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class ScheduleFileConverterImpl: ScheduleFileConverter {
    override fun convertInputStreamToScheduleDto(inputStream: InputStream): ScheduleDto? {
        return parseSchedule(inputStream)
    }

    private fun getWorkbook(inputStream: InputStream): Workbook {
        return WorkbookFactory.create(inputStream)
    }

    private fun getProgramme(programme: String): String {
        val programmeRegex = Regex("[А-Яа-яЁёa-zA-Z]+")
        return programmeRegex.find(programme)?.value ?: ""
    }

    private fun getCourse(sheetName: String): Int {
        val courseRegex = Regex(pattern = "[0-9]+")
        return (courseRegex.find(sheetName)?.value ?: "0").toInt()
    }

    private fun parseSchedule(inputStream: InputStream): ScheduleDto? {
        try {
            val workbook = getWorkbook(inputStream)
            val lessonsList = mutableListOf<LessonDto>()
            val scheduleInfo = getWeekInfo(getValue(
                workbook.getSheetAt(2),
                workbook.getSheetAt(1).getRow(1).getCell(3))
            )
            if (scheduleInfo.weekStartDate == null || scheduleInfo.weekEndDate == null) {
                return null
            }
            for (i in 0 until workbook.numberOfSheets) {
                val sheet = workbook.getSheetAt(i)
                if (sheet.sheetName.lowercase() == "доц") continue
                val course = getCourse(sheet.sheetName)
                val groups = mutableMapOf<Int, String>()
                val programs = mutableMapOf<Int, String>()
                for (cellNum in 2 until sheet.getRow(2).physicalNumberOfCells) {
                    val group = getValue(sheet, sheet.getRow(2).getCell(cellNum))
                    if (group != "") {
                        if (groups.containsValue(group).not()) {
                            groups[cellNum] = group
                            val programme = getProgramme(group)
                            programs[cellNum] = programme
                        }
                    }
                }
                run schedule@ {
                    for(rowNum in 3 until sheet.lastRowNum) {
                        val row = sheet.getRow(rowNum)
                        val unparsedDate = getValue(sheet, row.getCell(0)).split("\n")
                        val dates = mutableListOf<LocalDate>()
                        if (unparsedDate.size < 2) {
                            if(scheduleInfo.scheduleType != ScheduleType.QUARTER_SCHEDULE) break
                            val day = getDayOfWeek(unparsedDate[0]) ?: continue
                            var dateIteration = scheduleInfo.weekStartDate.plusDays(
                                day.ordinal.toLong() - scheduleInfo.weekStartDate.dayOfWeek.ordinal
                            )
                            while(dateIteration.isAfter(scheduleInfo.weekEndDate).not()) {
                                if (dateIteration.isBefore(LocalDate.now())) {
                                    dateIteration = dateIteration.plusDays(7)
                                    continue
                                }
                                dates.add(dateIteration)
                                dateIteration = dateIteration.plusDays(7)
                            }
                        } else {
                            dates.add(LocalDate.parse(unparsedDate[1], DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                        }
                        val timeCell = getValue(sheet, row.getCell(1)).split("\n").filter { it.isNotEmpty() }
                        if(timeCell.size < 2) continue
                        val timeRegex = Regex("[0-9]+:[0-9]+")
                        val timeRegexMatches = timeRegex.findAll(timeCell[1])
                        val startTime = timeRegexMatches.elementAt(0).value
                        val splitStartTime = startTime.split(":")
                        val startLocalTime = LocalTime.of(splitStartTime[0].toInt(), splitStartTime[1].toInt())
                        val endTime = timeRegexMatches.elementAt(1).value
                        val splitEndTime = endTime.split(":")
                        val endLocalTime = LocalTime.of(splitEndTime[0].toInt(), splitEndTime[1].toInt())
                        run line@ {
                            for (cellNum in 2 until row.physicalNumberOfCells) {
                                val cell = row.getCell(cellNum)
                                val cellValue = getValue(sheet = sheet, cell = cell)
                                if(cellValue.isEmpty()) continue
                                val group = groups.getOrDefault(cellNum, "")
                                if (group == "") {
                                    return@line
                                }
                                val programme = programs.getOrDefault(cellNum, "N/a")
                                val font = workbook.getFontAt(cell.cellStyle.fontIndex)
                                val isUnderlined = font.underline != Font.U_NONE
                                try {
                                    for (date in dates) {
                                        val startLocalDateTime = LocalDateTime.of(date, startLocalTime)
                                        val endLocalDateTime = LocalDateTime.of(date, endLocalTime)
                                        val lessons = getLesson(
                                            scheduleInfo = scheduleInfo,
                                            cell = CellInfo(
                                                value = cellValue,
                                                isUnderlined = isUnderlined,
                                            ),
                                            serviceLessonInfo = ServiceLessonInfo(
                                                course = course,
                                                programme = programme,
                                                group = group,
                                                date = date,
                                                startTimeStr = startTime,
                                                endTimeStr = endTime,
                                                startTime = startLocalDateTime,
                                                endTime = endLocalDateTime,
                                            )
                                        )
                                        for (lesson in lessons) {
                                            lessonsList.add(lesson)
                                        }
                                    }
                                } catch (e: Exception) {
                                    println("Произошла ошибка во время обработки пары!")
                                    println("cell: ${cellValue}")
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                }
            }
            lessonsList.sort()
            return ScheduleDto(
                uuid = UUID.randomUUID(), // TODO заменить
                weekNumber = scheduleInfo.weekNumber,
                weekStart = scheduleInfo.weekStartDate,
                weekEnd = scheduleInfo.weekEndDate,
                lessons = lessonsList,
                scheduleType = scheduleInfo.scheduleType,
                )
        } catch (exception: Exception) {
            exception.printStackTrace()
            println("Произошла ошибка во время обработки файла с расписанием!")
            return null
        }
    }

    private fun getValue(sheet: Sheet, cell: Cell?): String {
        if (cell == null) return ""
        for(region in sheet.mergedRegions){
            if(region.isInRange(cell)) {
                return sheet.getRow(region.firstRow).getCell(region.firstColumn).stringCellValue
            }
        }
        return cell.stringCellValue ?: ""
    }

    private fun getLesson(
        scheduleInfo: ParsedScheduleInfo,
        serviceLessonInfo: ServiceLessonInfo,
        cell: CellInfo
    ): List<LessonDto> {
        if(cell.value.lowercase().contains("сессия")) return listOf()
        val splitCell = cell.value.split("\n").toMutableList()
        splitCell.removeAll(listOf(""))
        val fields = getFieldsByType(splitCell)
        val rawLessons = getRawLessons(fields)
        val unmergedLessons = unmergeLessonFields(rawLessons)
        val lessons = clearIncorrectLessonFields(unmergedLessons)
        val checkedLessons = checkLessons(lessons)
        val builtLessons = mutableListOf<LessonDto>()
        checkedLessons.forEach {
            val additionalLessonInfo = getAdditionalLessonInfo(it)
            if (additionalLessonInfo.subGroups.isNotEmpty()) {
                additionalLessonInfo.subGroups.forEach { subGroup ->
                    builtLessons.add(buildLesson(
                        fields = it,
                        serviceLessonInfo = serviceLessonInfo,
                        additionalLessonInfo = additionalLessonInfo,
                        cell = cell,
                        scheduleInfo = scheduleInfo,
                        subGroup = subGroup
                    ))
                }
            }
            else {
                builtLessons.add(buildLesson(
                    fields = it,
                    serviceLessonInfo = serviceLessonInfo,
                    additionalLessonInfo = additionalLessonInfo,
                    cell = cell,
                    scheduleInfo = scheduleInfo,
                    subGroup = null
                ))
            }
        }
        return builtLessons
    }

    private fun getFieldsByType(cells: List<String>): List<LessonField> {
        val fields = mutableListOf<LessonField>()
        cells.forEach { cell ->
            var flag = false
            LINK_REGEX.findAll(cell)
                .forEach {
                    fields.add(LessonField(it.value.trim(), FieldType.LINK))
                    flag = true
                }
            if(LESSON_BUILDING_INFO_REGEX.find(cell) != null) {
                fields.add(LessonField(cell.trim(), FieldType.INFO))
                flag = true
            }
            if(!flag) {
                fields.add(LessonField(cell.trim(), FieldType.SUBJECT))
            }
        }
        return fields
    }

    private fun getRawLessons(fields: List<LessonField>): List<List<LessonField>> {
        val rawLessons = mutableListOf<List<LessonField>>()
        val tempLessonFields = mutableListOf<LessonField>()
        fields
            .forEach {
                if (it.fieldType == FieldType.SUBJECT) {
                    if(tempLessonFields.isNotEmpty()) {
                        rawLessons.add(tempLessonFields.toMutableList())
                    }
                    tempLessonFields.clear()
                    tempLessonFields.add(it)
                } else {
                    tempLessonFields.add(it)
                }
            }
        if(tempLessonFields.isNotEmpty()) {
            rawLessons.add(tempLessonFields.toMutableList())
        }
        return rawLessons
    }

    private fun unmergeLessonFields(fields: List<List<LessonField>>): List<List<LessonField>> {
        val unmergedLessonFields = mutableListOf<List<LessonField>>()
        fields.forEach { lessonFields ->
            val count = lessonFields.count { it.fieldType == FieldType.INFO }
            if (count <= 1) {
                unmergedLessonFields.add(lessonFields)
                return@forEach
            }
            var currentCount = 0
            val tempFields = mutableListOf<LessonField>()
            while(currentCount < count) {
                var i = 0
                var flag = false
                lessonFields.forEach fieldIterator@{ field ->
                    if(field.fieldType == FieldType.INFO) {
                        if (i == currentCount) {
                            tempFields.add(field)
                            flag = true
                        }
                        if(i > currentCount && flag.not()) return@fieldIterator
                        if(i > currentCount && flag) {
                            unmergedLessonFields.add(tempFields.toMutableList())
                            tempFields.clear()
                            flag = false
                            return@fieldIterator
                        }
                        i += 1
                    } else {
                        tempFields.add(field)
                    }
                }
                if(flag) {
                    unmergedLessonFields.add(tempFields.toMutableList())
                    tempFields.clear()
                    flag = false
                }
                currentCount += 1
            }
            if(tempFields.isNotEmpty()) {
                unmergedLessonFields.add(tempFields)
            }
        }
        return unmergedLessonFields
    }

    private fun clearIncorrectLessonFields(allLessonFields: List<List<LessonField>>): List<List<LessonField>> {
        val lessons = allLessonFields.map { it.toMutableList() }.toMutableList()
        if(allLessonFields.size > 1 && allLessonFields.any { lessonFields ->
                lessonFields.all { it.fieldType != FieldType.INFO }
            }) {
            allLessonFields.forEachIndexed { index, lessonFields ->
                if(index != 0) {
                    if (lessonFields.all { it.fieldType != FieldType.INFO }) {
                        lessonFields.forEach {
                            if (it.fieldType == FieldType.SUBJECT) {
                                lessons[index - 1].add(
                                    it.copy(
                                        fieldType = FieldType.ADDITIONAL
                                    )
                                )
                            }
                            else {
                                lessons[index - 1].add(it)
                            }
                        }
                        lessons.remove(lessonFields)
                    }
                }
            }
        }
        return lessons
    }

    private fun checkLessons(allLessons: List<List<LessonField>>): List<List<LessonField>> {
        val lessons = allLessons.map { it.toMutableList() }.toMutableList()
        lessons.forEach {
            if (it.size == 1 && it.first().fieldType == FieldType.INFO) {
                val match = LESSON_BUILDING_INFO_REGEX.containsMatchIn(it.first().value)
                if(match) {
                    val subject = it.first().value
                    val info = LESSON_BUILDING_INFO_REGEX.find(subject)?.value
                    if (info != null) {
                        it.removeAt(0)
                        it.add(LessonField(subject.replace(info, "").trim(), FieldType.SUBJECT))
                        it.add(LessonField(info, FieldType.INFO))
                    }
                }
            }
        }
        return lessons
    }

    private fun buildLesson(
        fields: List<LessonField>,
        cell: CellInfo,
        scheduleInfo: ParsedScheduleInfo,
        serviceLessonInfo: ServiceLessonInfo,
        additionalLessonInfo: AdditionalLessonInfo,
        subGroup: Int?
    ): LessonDto {
        val subject = fields.first { it.fieldType == FieldType.SUBJECT }.value.trim()
        val lessonType = getLessonType(
            isSessionWeek = scheduleInfo.scheduleType == ScheduleType.SESSION_WEEK_SCHEDULE,
            isUnderlined = cell.isUnderlined,
            subject = subject,
            lessonInfo = additionalLessonInfo.lecturer,
            isHaveBuildingInfo = fields.find { it.fieldType == FieldType.INFO } != null,
            additionalInfo = additionalLessonInfo.additionalInfo
        )
        return LessonDto(
            subject = lessonType.reformatSubject(subject),
            lessonType = lessonType,
            places = additionalLessonInfo.places,
            lecturer = additionalLessonInfo.lecturer,
            subGroup = subGroup,
            group =  serviceLessonInfo.group,
            course = serviceLessonInfo.course,
            date = serviceLessonInfo.date,
            programme = serviceLessonInfo.programme,
            startTimeStr = serviceLessonInfo.startTimeStr,
            endTimeStr = serviceLessonInfo.endTimeStr,
            parentScheduleType = scheduleInfo.scheduleType,
            links = additionalLessonInfo.links,
            additionalInfo = additionalLessonInfo.additionalInfo,
        )
    }

    private fun getAdditionalLessonInfo(fields: List<LessonField>): AdditionalLessonInfo {
        var lecturer: String? = null
        val placeMatches = mutableListOf<String>()
        val links = mutableListOf<String>()
        val additionalInfo = mutableListOf<String>()
        fields
            .filter { it.fieldType != FieldType.SUBJECT }
            .forEach { field ->
                if(field.fieldType == FieldType.LINK) {
                    links.add(field.value)
                } else if(field.fieldType == FieldType.INFO) {
                    val additionalInfoMatch = ADDITIONAL_INFO_REGEX.find(field.value)
                    if(additionalInfoMatch != null) {
                        val line = field.value.substring(0, additionalInfoMatch.range.first) + field.value.substring(additionalInfoMatch.range.last + 1)
                        if(line.isNotEmpty()) {
                            additionalInfo.add(line)
                        }
                        val additionalInfoRegexGroups = additionalInfoMatch.groups
                        if (additionalInfoRegexGroups.isEmpty().not()) {
                            lecturer = getLecturer(additionalInfoRegexGroups[1]?.value?.trim())?.replace("  ", " ")
                            val placeInfoLine = additionalInfoRegexGroups[2]?.value ?: return@forEach
                            val maybePlaces = placeInfoLine.replace("  ", " ").trim().split(",")
                            placeMatches.addAll(maybePlaces)
                        }
                    }
                } else if(field.fieldType == FieldType.ADDITIONAL) {
                    additionalInfo.add(field.value)
                }
            }
        val places = mutableListOf<LessonPlaceDto>()
        val subgroups = mutableListOf<Int>()
        val offices = mutableListOf<String>()
        placeMatches.forEach { match ->
            val placeMatch = PLACE_INFO_CHECK_REGEX.matches(match)
            if (placeMatch) {
                val match = PLACE_INFO_REGEX.findAll(match).toList()
                val office = match.getOrNull(0)?.value?.trim()
                if (office != null) {
                    offices.add(office)
                }
                val building = match.getOrNull(1)?.value?.toIntOrNull()
                offices.forEach {
                    places.add(LessonPlaceDto(
                        office = it,
                        building = building
                    ))
                }
                offices.clear()
            } else {
                if (match.trim().length in 1..2) {
                    val subgroup = match.trim().toIntOrNull()
                    if (subgroup != null) {
                        subgroups.add(subgroup)
                    } else {
                        offices.add(match.trim())
                    }
                }
                else {
                    offices.add(match.trim())
                }
            }
        }
        return AdditionalLessonInfo(
            lecturer = lecturer,
            places = if(places.isEmpty()) null else places,
            subGroups = subgroups,
            additionalInfo = if (additionalInfo.isEmpty()) null else additionalInfo,
            links = if (links.isEmpty()) null else links,
        )
    }

    private fun getLessonType(
        isSessionWeek: Boolean,
        subject: String,
        lessonInfo: String? = "",
        additionalInfo: List<String>? = null,
        isUnderlined: Boolean,
        isHaveBuildingInfo: Boolean,
    ): LessonType {
        val pureSubject = subject.lowercase()
        val pureLessonInfo = lessonInfo?.lowercase()
        val pureFullLessonInfo = pureSubject + " " + (additionalInfo?.joinToString { it.lowercase() } ?: "") + " " + (pureLessonInfo ?: "")
        if (pureFullLessonInfo.contains("ведомост")) return LessonType.STATEMENT
        if (pureFullLessonInfo.contains("независимый экзамен")) return LessonType.INDEPENDENT_EXAM
        if (pureFullLessonInfo.contains("экзамен")) return LessonType.EXAM
        if (pureFullLessonInfo.contains("зачёт") || pureSubject.contains("зачет")) return LessonType.TEST
        if (pureFullLessonInfo.contains("английский язык")) return LessonType.COMMON_ENGLISH
        if (pureFullLessonInfo.contains("майнор")) {
            if (isSessionWeek) return LessonType.EXAM
            return LessonType.COMMON_MINOR
        }
        if (pureSubject == "практика") return LessonType.PRACTICE
        if (pureFullLessonInfo.contains("мкд") || pureFullLessonInfo.contains("мдк")) return LessonType.ICC
        if (pureFullLessonInfo.contains("лекция") || pureSubject.contains("лекции")) return LessonType.LECTURE
        if (pureFullLessonInfo.contains("семинар") || pureSubject.contains("семинары")) return LessonType.SEMINAR
        if (pureFullLessonInfo.contains("доц по выбору")) return LessonType.UNDEFINED_AED
        if (pureFullLessonInfo.contains("доц")) return LessonType.AED
        if (isHaveBuildingInfo.not()) return LessonType.EVENT
        if (isUnderlined) return LessonType.LECTURE
        return LessonType.SEMINAR
    }

    private fun getWeekInfo(weekInfoStr: String): ParsedScheduleInfo {
        val weekInfoRegex = Regex("\\D*(\\d*).+\\s+(\\d+\\.\\d+\\.\\d+)\\s.+\\s(\\d+\\.\\d+\\.\\d+)")
        val weekInfoMatches = weekInfoRegex.findAll(weekInfoStr)
        val weekInfoGroups = weekInfoMatches.elementAt(0).groups
        val weekNumber = (weekInfoGroups.get(1)?.value?.trim())?.toIntOrNull()
        val datePattern = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val weekStart = LocalDate.parse(weekInfoGroups.get(2)?.value, datePattern)
        val weekEnd = LocalDate.parse(weekInfoGroups.get(3)?.value, datePattern)
        var scheduleType = ScheduleType.COMMON_WEEK_SCHEDULE
        if(weekNumber == null) {
            scheduleType = ScheduleType.SESSION_WEEK_SCHEDULE
        } else if(weekEnd.toEpochDay() - weekStart.toEpochDay() > 7) {
            scheduleType = ScheduleType.QUARTER_SCHEDULE
        }
        return ParsedScheduleInfo(
            weekNumber = weekNumber,
            weekStartDate = weekStart,
            weekEndDate = weekEnd,
            scheduleType = scheduleType,
        )
    }

    private fun getLecturer(str: String?): String? {
        if (str == null) return null
        if (str.isEmpty()) return null
        return str
    }

    private fun getDayOfWeek(str: String): DayOfWeek? {
        return when(str.lowercase()) {
            "понедельник" -> DayOfWeek.MONDAY
            "вторник" -> DayOfWeek.TUESDAY
            "среда" -> DayOfWeek.WEDNESDAY
            "четверг" -> DayOfWeek.THURSDAY
            "пятница" -> DayOfWeek.FRIDAY
            "суббота" -> DayOfWeek.SATURDAY
            "воскресенье" -> DayOfWeek.SUNDAY
            else -> null
        }
    }

    companion object {
        private val LESSON_BUILDING_INFO_REGEX = Regex("\\([^\\(\\)]*\\[\\d*\\].*\\)")
        private val LINK_REGEX = Regex("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
        private val ADDITIONAL_INFO_REGEX = Regex("([^\\/]*)\\((.*)\\)")
        private val PLACE_INFO_REGEX = Regex("\\A[.[^\\[]]+|\\d+")
        private val PLACE_INFO_CHECK_REGEX = Regex("(.+)\\[\\d\\]")
    }

    internal data class ParsedScheduleInfo(
        val weekNumber: Int?,
        val weekStartDate: LocalDate?,
        val weekEndDate: LocalDate?,
        val scheduleType: ScheduleType,
    )

    internal enum class FieldType {
        SUBJECT,
        INFO,
        LINK,
        ADDITIONAL,
    }
    internal data class LessonField(
        val value: String,
        val fieldType: FieldType
    )

    internal data class CellInfo(
        val value: String,
        val isUnderlined: Boolean,
    )

    internal data class ServiceLessonInfo(
        val course: Int,
        val programme: String,
        val group: String,
        val date: LocalDate,
        val startTimeStr: String,
        val endTimeStr: String,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
    )

    internal data class AdditionalLessonInfo(
        val lecturer: String?,
        val places: List<LessonPlaceDto>?,
        val subGroups: List<Int>,
        val links: List<String>?,
        val additionalInfo: List<String>?,
    )
}