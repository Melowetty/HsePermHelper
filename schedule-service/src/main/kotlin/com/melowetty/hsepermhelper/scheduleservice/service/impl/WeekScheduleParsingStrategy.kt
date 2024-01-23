package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseLessonDto
import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.dto.WeekLessonDto
import com.melowetty.hsepermhelper.scheduleservice.dto.WeekScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import com.melowetty.hsepermhelper.scheduleservice.service.BaseScheduleParsingStrategy
import com.melowetty.hsepermhelper.scheduleservice.utils.ParserUtils
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class WeekScheduleParsingStrategy: BaseScheduleParsingStrategy {
    override fun parseSchedule(uuid: UUID, workbook: Workbook, scheduleInfo: ParserUtils.ParsedScheduleInfo): BaseScheduleDto? {
        try {
            val parsedLessons = mutableListOf<BaseLessonDto>()
            for (i in 0 until workbook.numberOfSheets) {
                val sheet = workbook.getSheetAt(i)
                if (sheet.sheetName.lowercase() == "доц") continue
                val course = ParserUtils.getCourse(sheet.sheetName)
                run schedule@{
                    for (rowNum in 3 until sheet.lastRowNum) {
                        val row = sheet.getRow(rowNum)
                        val unparsedDate = ParserUtils.getValue(sheet, row.getCell(0)).split("\n")
                        if (unparsedDate.size < 2) break
                        val date = LocalDate.parse(unparsedDate[1], DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                        val (groups, programs) = ParserUtils.getGroupsAndProgramsFromSheet(sheet)
                        val (startTime, endTime) = ParserUtils.getStartAndEndOfLessonAsString(sheet, row) ?: continue
                        run line@{
                            for (cellNum in 2 until row.physicalNumberOfCells) {
                                val cell = row.getCell(cellNum)
                                val cellValue = ParserUtils.getValue(sheet = sheet, cell = cell)
                                if (cellValue.isEmpty()) continue
                                val group = groups.getOrDefault(cellNum, "")
                                if (group == "") {
                                    return@line
                                }
                                val programme = programs.getOrDefault(cellNum, "N/a")
                                val isUnderlined = ParserUtils.isUnderlined(workbook, cell)
                                try {
                                    val lessons = getLesson(
                                        scheduleInfo = scheduleInfo,
                                        cell = ParserUtils.CellInfo(
                                            value = cellValue,
                                            isUnderlined = isUnderlined,
                                        ),
                                        serviceLessonInfo = ParserUtils.ServiceLessonInfo(
                                            course = course,
                                            programme = programme,
                                            group = group,
                                            startTime = startTime,
                                            endTime = endTime,
                                        ),
                                        date = date,
                                    )
                                    parsedLessons.addAll(lessons)
                                } catch (e: Exception) {
                                    println("Произошла ошибка во время обработки пары!")
                                    println("cell: $cellValue")
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                }
            }
            parsedLessons.sort()
            return WeekScheduleDto(
                uuid = uuid,
                weekNumber = scheduleInfo.scheduleNumber,
                weekStart = scheduleInfo.scheduleStartDate!!,
                weekEnd = scheduleInfo.scheduleEndDate!!,
                lessons = parsedLessons,
                scheduleType = scheduleInfo.scheduleType,
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            println("Произошла ошибка во время обработки файла с расписанием!")
            return null
        }
    }

    override fun getCompatibleScheduleTypes(): List<ScheduleType> {
        return listOf(ScheduleType.SESSION_WEEK_SCHEDULE, ScheduleType.COMMON_WEEK_SCHEDULE)
    }

    private fun getLesson(
        scheduleInfo: ParserUtils.ParsedScheduleInfo,
        date: LocalDate,
        serviceLessonInfo: ParserUtils.ServiceLessonInfo,
        cell: ParserUtils.CellInfo
    ): List<BaseLessonDto> {
        val checkedLessons = ParserUtils.cleanLessons(cell.value)
        val builtLessons = mutableListOf<BaseLessonDto>()
        checkedLessons.forEach {
            val additionalLessonInfo = ParserUtils.getAdditionalLessonInfo(it)
            if (additionalLessonInfo.subGroups.isNotEmpty()) {
                additionalLessonInfo.subGroups.forEach { subGroup ->
                    builtLessons.add(
                        buildLesson(
                            fields = it,
                            serviceLessonInfo = serviceLessonInfo,
                            additionalLessonInfo = additionalLessonInfo,
                            cell = cell,
                            scheduleInfo = scheduleInfo,
                            subGroup = subGroup,
                            date = date,
                        )
                    )
                }
            } else {
                builtLessons.add(
                    buildLesson(
                        fields = it,
                        serviceLessonInfo = serviceLessonInfo,
                        additionalLessonInfo = additionalLessonInfo,
                        cell = cell,
                        scheduleInfo = scheduleInfo,
                        subGroup = null,
                        date = date,
                    )
                )
            }
        }
        return builtLessons
    }

    private fun buildLesson(
        fields: List<ParserUtils.LessonField>,
        cell: ParserUtils.CellInfo,
        scheduleInfo: ParserUtils.ParsedScheduleInfo,
        date: LocalDate,
        serviceLessonInfo: ParserUtils.ServiceLessonInfo,
        additionalLessonInfo: ParserUtils.AdditionalLessonInfo,
        subGroup: Int?
    ): BaseLessonDto {
        val subject = fields.first { it.fieldType == ParserUtils.FieldType.SUBJECT }.value.trim()
        val lessonType = ParserUtils.getLessonType(
            isSessionWeek = scheduleInfo.scheduleType == ScheduleType.SESSION_WEEK_SCHEDULE,
            isUnderlined = cell.isUnderlined,
            subject = subject,
            lessonInfo = additionalLessonInfo.lecturer,
            isHaveBuildingInfo = fields.find { it.fieldType == ParserUtils.FieldType.INFO } != null,
            additionalInfo = additionalLessonInfo.additionalInfo
        )
        return WeekLessonDto(
            subject = lessonType.reformatSubject(subject),
            lessonType = lessonType,
            places = additionalLessonInfo.places,
            lecturer = additionalLessonInfo.lecturer,
            subGroup = subGroup,
            group = serviceLessonInfo.group,
            course = serviceLessonInfo.course,
            date = date,
            programme = serviceLessonInfo.programme,
            startTime = serviceLessonInfo.startTime,
            endTime = serviceLessonInfo.endTime,
            parentScheduleType = scheduleInfo.scheduleType,
            links = additionalLessonInfo.links,
            additionalInfo = additionalLessonInfo.additionalInfo,
        )
    }
}