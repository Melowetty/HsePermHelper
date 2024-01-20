package com.melowetty.hsepermhelper.usersservice.dto

import java.time.LocalDateTime
import java.util.*

data class UserDto(
    val id: UUID,

    val creationDate: LocalDateTime,

    val settings: SettingsDto,

    val telegramInfo: TelegramInfoDto? = null

)