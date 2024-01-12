package com.melowetty.hsepermhelper.personalscheduleservice.dto

data class ScheduleFilter(
    val groupId: Long,
    val baseSubGroup: Int?,
    val subgroupSelects: Map<Long, Int>,
    val hiddenSubjects: Set<Long>
)
