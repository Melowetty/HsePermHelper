package com.melowetty.hsepermhelper.personalscheduleservice.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.util.UUID

@Entity
data class ScheduleUser(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid: UUID = UUID.randomUUID(),

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "settings_id")
    val settings: ScheduleSettings,
)
