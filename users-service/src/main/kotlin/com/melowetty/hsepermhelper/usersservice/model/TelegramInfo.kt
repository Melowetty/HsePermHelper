package com.melowetty.hsepermhelper.usersservice.model

data class TelegramInfo(
    val telegramId: Long,
    val firstName: String,
    val lastName: String?,
    val username: String?
)