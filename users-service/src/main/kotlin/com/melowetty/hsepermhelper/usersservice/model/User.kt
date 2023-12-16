package com.melowetty.hsepermhelper.usersservice.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "telegram_id", unique = true)
    val telegramId: Long,

    @CreationTimestamp
    @Column(name = "creation_date")
    val creationDate: LocalDateTime? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "settings_id")
    val settings: Settings
)
