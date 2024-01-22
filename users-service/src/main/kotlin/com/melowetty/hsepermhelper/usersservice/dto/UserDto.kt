package com.melowetty.hsepermhelper.usersservice.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.*

data class UserDto(
    val id: String,

    @JsonProperty(value = "creation_date")
    val creationDate: LocalDateTime,

    val settings: Map<String, Any>,

    @JsonProperty(value = "telegram_info")
    val telegramInfo: TelegramInfoDto

)