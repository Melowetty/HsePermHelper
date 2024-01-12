package com.melowetty.hsepermhelper.personalscheduleservice.dto

data class ScheduleSettingsDto(
    val baseSubGroup: Int,

    val groupId: Long,

    val subGroupSelect: Map<Long, Int?> = mapOf(),

    val hiddenSubjects: Set<Long> = setOf(),

    val includeQuarterSchedule: Boolean = false,

    val includeCommonEnglish: Boolean = false,

    val includeCommonMinor: Boolean = true,
)