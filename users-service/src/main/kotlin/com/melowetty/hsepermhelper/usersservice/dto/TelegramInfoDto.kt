package com.melowetty.hsepermhelper.usersservice.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TelegramInfoDto(
    @JsonProperty(value = "telegram_id")
    val telegramId: Long,
    @JsonProperty(value = "first_name")
    val firstName: String?,
    @JsonProperty(value = "second_name")
    val lastName: String?,
    @JsonProperty(value = "username")
    val username: String?
)