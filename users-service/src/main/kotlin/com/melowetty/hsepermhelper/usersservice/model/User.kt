package com.melowetty.hsepermhelper.usersservice.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "users")
data class User(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @CreatedDate
    val creationDate: LocalDateTime = LocalDateTime.now(),

    val settings: Settings,

    val telegramInfo: TelegramInfo,
)
