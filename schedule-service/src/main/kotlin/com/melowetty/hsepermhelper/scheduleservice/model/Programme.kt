package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "course"]),
    ]
)
data class Programme(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long?,
    val name: String,
    val fullName: String?,
    val translatedName: String?,
    val translatedFullName: String?,
    val course: Int,
)
