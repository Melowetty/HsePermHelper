package com.melowetty.hsepermhelper.personalscheduleservice.dto

import java.util.*

data class ScheduleUserDto(
    val id: UUID = UUID.randomUUID(),
    val settings: ScheduleSettingsDto,
)