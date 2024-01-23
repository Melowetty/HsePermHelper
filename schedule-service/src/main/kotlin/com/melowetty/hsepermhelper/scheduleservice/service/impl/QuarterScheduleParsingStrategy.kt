package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.*
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import com.melowetty.hsepermhelper.scheduleservice.service.BaseScheduleParsingStrategy
import com.melowetty.hsepermhelper.scheduleservice.utils.ParserUtils
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Component
import java.time.DayOfWeek
import java.util.*

@Component
class QuarterScheduleParsingStrategy: BaseScheduleParsingStrategy {
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
                        val dayOfWeek = ParserUtils.getDayOfWeek(unparsedDate[0]) ?: break
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
                                        dayOfWeek = dayOfWeek,
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
            return QuarterScheduleDto(
                uuid = uuid,
                quarterNumber = scheduleInfo.scheduleNumber!!,
                quarterStart = scheduleInfo.scheduleStartDate!!,
                quarterEnd = scheduleInfo.scheduleEndDate!!,
                lessons = parsedLessons,
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            println("Произошла ошибка во время обработки файла с расписанием!")
            return null
        }
    }

    override fun getCompatibleScheduleTypes(): List<ScheduleType> {
        return listOf(ScheduleType.QUARTER_SCHEDULE)
    }

    private fun getLesson(
        scheduleInfo: ParserUtils.ParsedScheduleInfo,
        dayOfWeek: DayOfWeek,
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
                            dayOfWeek = dayOfWeek,
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
                        dayOfWeek = dayOfWeek,
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
        dayOfWeek: DayOfWeek,
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
        return QuarterLessonDto(
            subject = lessonType.reformatSubject(subject),
            lessonType = lessonType,
            places = additionalLessonInfo.places,
            lecturer = additionalLessonInfo.lecturer,
            subGroup = subGroup,
            group = serviceLessonInfo.group,
            course = serviceLessonInfo.course,
            dayOfWeek = dayOfWeek,
            programme = serviceLessonInfo.programme,
            startTime = serviceLessonInfo.startTime,
            endTime = serviceLessonInfo.endTime,
            parentScheduleType = scheduleInfo.scheduleType,
            links = additionalLessonInfo.links,
            additionalInfo = additionalLessonInfo.additionalInfo,
        )
    }
}