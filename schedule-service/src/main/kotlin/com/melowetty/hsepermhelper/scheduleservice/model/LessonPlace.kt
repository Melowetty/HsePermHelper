package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*

@Entity
data class LessonPlace(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
    val office: String?,
    val building: Int?,
)