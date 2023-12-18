package com.melowetty.hsepermhelper.usersservice.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация от телеграмма о пользователе")
data class TelegramInfoDto(
    @Schema(description = "Имя пользователя в Telegram")
    val firstName: String,
    @Schema(description = "Фамилия пользователя в Telegram")
    val lastName: String?,
    @Schema(description = "Тэг в Telegram")
    val username: String?
)