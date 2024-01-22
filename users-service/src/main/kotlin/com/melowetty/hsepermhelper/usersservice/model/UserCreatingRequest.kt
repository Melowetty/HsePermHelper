package com.melowetty.hsepermhelper.usersservice.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserCreatingRequest(
    val settings: Map<String, Any>,
    @JsonProperty(value = "telegram_info")
    val telegramInfo: TelegramInfoCreatingRequest
)