package com.melowetty.hsepermhelper.scheduleservice.dto

import com.melowetty.hsepermhelper.scheduleservice.model.LessonType
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Учебная пара")
abstract class BaseLessonDto(
    @Schema(description = "Учебный предмет", example = "Программирование")
    val subject: String,
    val course: Int,
    val programme: String,
    val group: String,
    val subGroup: Int?,
    @Schema(description = "Время начала пары", example = "8:10")
    val startTime: String,
    @Schema(description = "Время окончания пары", example = "9:30")
    val endTime: String,
    @Schema(description = "Преподаватель", example = "Викентьева О.Л.", nullable = true)
    val lecturer: String?,
    @Schema(description = "Место проведения", nullable = true)
    val places: List<LessonPlaceDto>? = null,
    @Schema(description = "Ссылки на пару (null - если ссылок нет)")
    val links: List<String>? = null,
    @Schema(description = "Дополнительная информация о паре (null - если информации нет)")
    val additionalInfo: List<String>? = null,
    @Schema(description = "Тип лекции", example = "SEMINAR")
    val lessonType: LessonType,
    @Schema(description = "Тип расписания-родителя", example = "COMMON_WEEK_SCHEDULE")
    val parentScheduleType: ScheduleType,
): Comparable<BaseLessonDto>