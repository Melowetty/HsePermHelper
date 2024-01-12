package com.melowetty.hsepermhelper.scheduleservice.dto

data class LessonsFilter(
    val groupId: Long,
    val baseSubGroup: Int?,
    val subgroupSelects: Map<Long, Int>,
    val hiddenSubjects: Set<Long>
)
