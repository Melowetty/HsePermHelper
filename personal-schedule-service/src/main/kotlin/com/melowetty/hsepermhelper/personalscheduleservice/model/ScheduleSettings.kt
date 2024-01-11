package com.melowetty.hsepermhelper.personalscheduleservice.model

import jakarta.persistence.*

@Entity
data class ScheduleSettings(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    val baseSubGroup: Int,

    val groupId: Long,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "schedule_settings_id")
    val subGroupSelect: Set<SubGroupSelect>,

    @ElementCollection
    val bannedSubjects: Set<Long>,

    @Column(name = "include_quarter_schedule", columnDefinition="BOOLEAN DEFAULT false")
    val includeQuarterSchedule: Boolean = false,

    @Column(name = "include_common_english", columnDefinition="BOOLEAN DEFAULT false")
    val includeCommonEnglish: Boolean = false,

    @Column(name = "include_common_minor", columnDefinition="BOOLEAN DEFAULT true")
    val includeCommonMinor: Boolean = true,
)