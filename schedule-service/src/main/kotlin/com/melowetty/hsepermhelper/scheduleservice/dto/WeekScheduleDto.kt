package com.melowetty.hsepermhelper.scheduleservice.dto

import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.util.*

class WeekScheduleDto(
    uuid: UUID,
    @Schema(description = "Номер недели", example = "4")
    val weekNumber: Int?,
    lessons: List<BaseLessonDto>,
    weekStart: LocalDate,
    weekEnd: LocalDate,
    scheduleType: ScheduleType
): BaseScheduleDto(
    uuid = uuid,
    lessons = lessons,
    scheduleStart = weekStart,
    scheduleEnd = weekEnd,
    scheduleType = scheduleType,
)