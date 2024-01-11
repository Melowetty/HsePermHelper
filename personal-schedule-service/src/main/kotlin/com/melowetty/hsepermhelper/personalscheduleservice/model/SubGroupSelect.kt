package com.melowetty.hsepermhelper.personalscheduleservice.model

import jakarta.persistence.*

@Entity
data class SubGroupSelect(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long = 0,
    val subjectId: Long,
    val subGroup: Int?,
)
