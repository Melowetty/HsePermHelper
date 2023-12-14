package com.melowetty.hsepermhelper.usersservice.model

import jakarta.persistence.*

@Entity
@Table(name = "settings")
data class Settings(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
    @Enumerated(EnumType.STRING)
    val language: Language,

    @Column(name = "include_quarter_schedule", columnDefinition="BOOLEAN DEFAULT false")
    val includeQuarterSchedule: Boolean = false,

    @Column(name = "include_common_english", columnDefinition="BOOLEAN DEFAULT false")
    val includeCommonEnglish: Boolean = false,

    @Column(name = "include_common_minor", columnDefinition="BOOLEAN DEFAULT true")
    val includeCommonMinor: Boolean = true,

    @Column(name = "is_enabled_remote_calendar", columnDefinition="BOOLEAN DEFAULT false")
    val isEnabledRemoteCalendar: Boolean = false,

    @Column(name = "is_enabled_new_common_schedule_notification", columnDefinition="BOOLEAN DEFAULT true")
    val isEnabledNewCommonScheduleNotifications: Boolean = true,

    @Column(name = "is_enabled_new_quarter_schedule_notification", columnDefinition="BOOLEAN DEFAULT false")
    val isEnabledNewQuarterScheduleNotifications: Boolean = false,
)