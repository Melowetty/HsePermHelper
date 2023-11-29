package com.melowetty.hsepermhelper.usersservice.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "telegram_id", unique = true)
    val telegramId: Long,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "settings_id")
    val settings: Settings
)
