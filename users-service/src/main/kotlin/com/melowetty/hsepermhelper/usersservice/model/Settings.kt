package com.melowetty.hsepermhelper.usersservice.model

import com.melowetty.languagessupportlibrary.model.Language
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

    @Column(name = "is_enabled_remote_calendar", columnDefinition="BOOLEAN DEFAULT false")
    val isEnabledRemoteCalendar: Boolean = false,

    @Column(name = "is_enabled_new_common_schedule_notification", columnDefinition="BOOLEAN DEFAULT true")
    val isEnabledNewCommonScheduleNotifications: Boolean = true,

    @Column(name = "is_enabled_new_quarter_schedule_notification", columnDefinition="BOOLEAN DEFAULT false")
    val isEnabledNewQuarterScheduleNotifications: Boolean = false,
)