package com.melowetty.hsepermhelper.scheduleservice.utils

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.model.LessonType
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ParserUtils {
    companion object {
        val LESSON_BUILDING_INFO_REGEX = Regex("\\([^\\(\\)]*\\[\\d*\\].*\\)")
        val LINK_REGEX = Regex("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)")
        val ADDITIONAL_INFO_REGEX = Regex("([^\\/]*)\\((.*)\\)")
        val PLACE_INFO_REGEX = Regex("\\A[.[^\\[]]+|\\d+")
        val PLACE_INFO_CHECK_REGEX = Regex("(.+)\\[\\d\\]")
        fun getValue(sheet: Sheet, cell: Cell?): String {
            if (cell == null) return ""
            for(region in sheet.mergedRegions){
                if(region.isInRange(cell)) {
                    return sheet.getRow(region.firstRow).getCell(region.firstColumn).stringCellValue
                }
            }
            return cell.stringCellValue ?: ""
        }

        fun getWeekInfo(weekInfoStr: String): ParsedScheduleInfo {
            val scheduleInfoRegex = Regex("\\D*(\\d*).+\\s+(\\d+\\.\\d+\\.\\d+)\\s.+\\s(\\d+\\.\\d+\\.\\d+)")
            val scheduleInfoMatches = scheduleInfoRegex.findAll(weekInfoStr)
            val scheduleInfoGroups = scheduleInfoMatches.elementAt(0).groups
            val scheduleNumber = (scheduleInfoGroups.get(1)?.value?.trim())?.toIntOrNull()
            val datePattern = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val scheduleStart = LocalDate.parse(scheduleInfoGroups.get(2)?.value, datePattern)
            val scheduleEnd = LocalDate.parse(scheduleInfoGroups.get(3)?.value, datePattern)
            var scheduleType = ScheduleType.COMMON_WEEK_SCHEDULE
            if(scheduleNumber == null) {
                scheduleType = ScheduleType.SESSION_WEEK_SCHEDULE
            } else if(scheduleEnd.toEpochDay() - scheduleStart.toEpochDay() > 7) {
                scheduleType = ScheduleType.QUARTER_SCHEDULE
            }
            return ParsedScheduleInfo(
                scheduleNumber = scheduleNumber,
                scheduleStartDate = scheduleStart,
                scheduleEndDate = scheduleEnd,
                scheduleType = scheduleType,
            )
        }

        fun getDayOfWeek(str: String): DayOfWeek? {
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

        fun getLecturer(str: String?): String? {
            if (str == null) return null
            if (str.isEmpty()) return null
            return str
        }

        fun getLessonType(
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

        private fun getFieldsByType(cells: List<String>): List<LessonField> {
            val fields = mutableListOf<LessonField>()
            cells.forEach { cell ->
                var flag = false
                LINK_REGEX.findAll(cell)
                    .forEach {
                        fields.add(
                            LessonField(
                                it.value.trim(),
                                FieldType.LINK
                            )
                        )
                        flag = true
                    }
                if(LESSON_BUILDING_INFO_REGEX.find(cell) != null) {
                    fields.add(
                        LessonField(
                            cell.trim(),
                            FieldType.INFO
                        )
                    )
                    flag = true
                }
                if(!flag) {
                    fields.add(
                        LessonField(
                            cell.trim(),
                            FieldType.SUBJECT
                        )
                    )
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
                            it.add(
                                LessonField(
                                    subject.replace(info, "").trim(),
                                    FieldType.SUBJECT
                                )
                            )
                            it.add(
                                LessonField(
                                    info,
                                    FieldType.INFO
                                )
                            )
                        }
                    }
                }
            }
            return lessons
        }

        fun cleanLessons(cellValue: String): List<List<LessonField>> {
            if(cellValue.lowercase().contains("сессия")) return listOf()
            val splitCell = cellValue.split("\n").toMutableList()
            splitCell.removeAll(listOf(""))
            val fieldsByType = getFieldsByType(splitCell)
            val rawLessons = getRawLessons(fieldsByType)
            val unmergedLessonFields = unmergeLessonFields(rawLessons)
            val clearedIncorrectLessonFields = clearIncorrectLessonFields(unmergedLessonFields)
            return checkLessons(clearedIncorrectLessonFields)
        }

        fun getAdditionalLessonInfo(fields: List<LessonField>): AdditionalLessonInfo {
            var lecturer: String? = null
            val placeMatches = mutableListOf<String>()
            val links = mutableListOf<String>()
            val additionalInfo = mutableListOf<String>()
            fields
                .filter { it.fieldType != FieldType.SUBJECT }
                .forEach { field ->
                    if (field.fieldType == FieldType.LINK) {
                        links.add(field.value)
                    } else if (field.fieldType == FieldType.INFO) {
                        val additionalInfoMatch = ADDITIONAL_INFO_REGEX.find(field.value)
                        if (additionalInfoMatch != null) {
                            val line =
                                field.value.substring(0, additionalInfoMatch.range.first) + field.value.substring(
                                    additionalInfoMatch.range.last + 1
                                )
                            if (line.isNotEmpty()) {
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
                    } else if (field.fieldType == FieldType.ADDITIONAL) {
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
                        places.add(
                            LessonPlaceDto(
                                office = it,
                                building = building
                            )
                        )
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
                    } else {
                        offices.add(match.trim())
                    }
                }
            }
            return AdditionalLessonInfo(
                lecturer = lecturer,
                places = if (places.isEmpty()) null else places,
                subGroups = subgroups,
                additionalInfo = if (additionalInfo.isEmpty()) null else additionalInfo,
                links = if (links.isEmpty()) null else links,
            )
        }

        fun getProgramme(programme: String): String {
            val programmeRegex = Regex("[А-Яа-яЁёa-zA-Z]+")
            return programmeRegex.find(programme)?.value ?: ""
        }

        fun getCourse(sheetName: String): Int {
            val courseRegex = Regex(pattern = "[0-9]+")
            return (courseRegex.find(sheetName)?.value ?: "0").toInt()
        }

        fun getGroupsAndProgramsFromSheet(sheet: Sheet): Pair<Map<Int, String>, Map<Int, String>> {
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
            return Pair(groups, programs)
        }

        fun getStartAndEndOfLessonAsString(sheet: Sheet, row: Row): Pair<String, String>? {
            val timeCell = getValue(sheet, row.getCell(1)).split("\n").filter { it.isNotEmpty() }
            if(timeCell.size < 2) return null
            val timeRegex = Regex("[0-9]+:[0-9]+")
            val timeRegexMatches = timeRegex.findAll(timeCell[1])
            val startTime = timeRegexMatches.elementAt(0).value
            val endTime = timeRegexMatches.elementAt(1).value
            return Pair(startTime, endTime)
        }

        fun getStartAndEndOfLesson(sheet: Sheet, row: Row): Pair<LocalTime, LocalTime>? {
            val (startTime, endTime) = getStartAndEndOfLessonAsString(sheet, row) ?: return null
            val splitStartTime = startTime.split(":")
            val startLocalTime = LocalTime.of(splitStartTime[0].toInt(), splitStartTime[1].toInt())
            val splitEndTime = endTime.split(":")
            val endLocalTime = LocalTime.of(splitEndTime[0].toInt(), splitEndTime[1].toInt())
            return Pair(startLocalTime, endLocalTime)
        }

        fun isUnderlined(workbook: Workbook, cell: Cell): Boolean {
            val font = workbook.getFontAt(cell.cellStyle.fontIndex)
            return font.underline != Font.U_NONE
        }
    }


    data class ParsedScheduleInfo(
        val scheduleNumber: Int?,
        val scheduleStartDate: LocalDate?,
        val scheduleEndDate: LocalDate?,
        val scheduleType: ScheduleType,
    )

    enum class FieldType {
        SUBJECT,
        INFO,
        LINK,
        ADDITIONAL,
    }

    data class LessonField(
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
        val startTime: String,
        val endTime: String,
    )

    data class AdditionalLessonInfo(
        val lecturer: String?,
        val places: List<LessonPlaceDto>?,
        val subGroups: List<Int>,
        val links: List<String>?,
        val additionalInfo: List<String>?,
    )
}