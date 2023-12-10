package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "course"]),
        //UniqueConstraint(columnNames = ["translatedName", "course"])
    ]
)
data class Programme(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long?,
    val name: String,
    val translatedName: String?,
    val course: Int,
)
