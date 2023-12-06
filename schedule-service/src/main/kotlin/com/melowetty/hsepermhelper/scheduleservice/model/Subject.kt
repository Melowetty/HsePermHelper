package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*

@Entity
data class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long?,
    val name: String,
    val translatedName: String?,
    val course: Int,
    @ManyToOne
    @JoinColumn(name = "subject_id")
    val group: Group
)