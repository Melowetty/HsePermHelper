package com.melowetty.hsepermhelper.usersservice.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(name = "Пользователь")
data class UserDto(
    @Schema(description = "ID пользователя", example = "UUID")
    val id: UUID = UUID.randomUUID(),

    @Schema(description = "Telegram ID пользователя", example = "123432412")
    val telegramId: Long,

    @Schema(description = "Дата создания пользователя", example = "10.05.2023 10:12:23")
    val creationDate: LocalDateTime = LocalDateTime.now(),

    @Schema(description = "Настройки пользователя")
    val settings: SettingsDto,

    @Schema(description = "Информация от телеграмма о пользователе")
    val telegramInfo: TelegramInfoDto? = null

)