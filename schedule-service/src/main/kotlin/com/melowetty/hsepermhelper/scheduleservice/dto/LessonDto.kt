package com.melowetty.hsepermhelper.scheduleservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.melowetty.hsepermhelper.scheduleservice.model.LessonType
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Учебная пара")
data class LessonDto(
    @Schema(description = "Учебный предмет", example = "Программирование")
    val subject: String,
    val course: Int,
    val programme: String,
    val group: String,
    val subGroup: Int?,
    @Schema(description = "Дата пары", example = "03.09.2023", type = "string")
    val date: LocalDate,
    @Schema(description = "Время начала пары", example = "8:10")
    @JsonProperty("startTime")
    val startTimeStr: String,
    @Schema(description = "Время окончания пары", example = "9:30")
    @JsonProperty("endTime")
    val endTimeStr: String,
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
): Comparable<LessonDto> {
    override fun compareTo(other: LessonDto): Int {
        return date.compareTo(other.date)
    }

}