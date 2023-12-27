package com.melowetty.hsepermhelper.scheduleservice.dto

import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate
import java.util.*

@Schema(description = "Расписание занятий")
abstract class BaseScheduleDto(
    @Schema(description = "Уникальный идентификатор расписания")
    val uuid: UUID,
    @Schema(description = "Номер недели", example = "6", nullable = true)
    val lessons: List<BaseLessonDto>,
    @Schema(description = "Дата начала недели", example = "03.09.2023", type = "string")
    val scheduleStart: LocalDate,
    @Schema(description = "Дата конца недели", example = "10.09.2023", type = "string")
    val scheduleEnd: LocalDate,
    @Schema(description = "Тип расписания")
    val scheduleType: ScheduleType
)