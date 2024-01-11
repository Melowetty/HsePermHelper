package com.melowetty.hsepermhelper.personalscheduleservice.dto

data class ScheduleSettingsDto(
    val baseSubGroup: Int,

    val groupId: Long,

    val subGroupSelect: Map<Long, Int?>,

    val bannedSubjects: Set<Long>,

    val includeQuarterSchedule: Boolean = false,

    val includeCommonEnglish: Boolean = false,

    val includeCommonMinor: Boolean = true,
)