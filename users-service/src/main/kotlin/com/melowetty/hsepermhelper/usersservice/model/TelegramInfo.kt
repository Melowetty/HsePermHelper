package com.melowetty.hsepermhelper.usersservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "telegram_info")
data class TelegramInfo(
    @Id
    val id: Long = 0L,
    val firstName: String,
    val lastName: String?,
    val username: String?
)