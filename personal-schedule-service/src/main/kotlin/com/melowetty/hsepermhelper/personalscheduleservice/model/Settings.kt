package com.melowetty.hsepermhelper.personalscheduleservice.model

data class Settings(
    val baseSubGroup: Int,
    val groupId: Long,
    val subGroupSelect: List<SubGroupSelect>
)