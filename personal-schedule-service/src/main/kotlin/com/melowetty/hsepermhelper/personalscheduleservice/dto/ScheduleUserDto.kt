package com.melowetty.hsepermhelper.personalscheduleservice.dto

import java.util.*

data class ScheduleUserDto(
    val id: UUID,
    val settings: ScheduleSettingsDto,
)