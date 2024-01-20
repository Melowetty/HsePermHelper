package com.melowetty.hsepermhelper.usersservice.dto

data class TelegramInfoDto(
    val telegramId: Long,
    val firstName: String,
    val lastName: String?,
    val username: String?
)