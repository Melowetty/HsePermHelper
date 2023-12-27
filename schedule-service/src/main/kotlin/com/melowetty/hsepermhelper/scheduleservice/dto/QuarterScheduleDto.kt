package com.melowetty.hsepermhelper.scheduleservice.dto

import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.util.*

class QuarterScheduleDto(
    uuid: UUID,
    @Schema(description = "Номер модуля", example = "3")
    val quarterNumber: Int,
    lessons: List<BaseLessonDto>,
    quarterStart: LocalDate,
    quarterEnd: LocalDate,
): BaseScheduleDto(
    uuid = uuid,
    lessons = lessons,
    scheduleStart = quarterStart,
    scheduleEnd = quarterEnd,
    scheduleType = ScheduleType.QUARTER_SCHEDULE,
)