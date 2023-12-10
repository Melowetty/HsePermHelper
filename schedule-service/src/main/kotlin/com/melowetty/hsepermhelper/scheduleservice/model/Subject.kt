package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["name", "programme_id"]),
        //UniqueConstraint(columnNames = ["translatedName", "course"])
    ]
)
data class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
    val name: String,
    val translatedName: String?,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "programme_id")
    val programme: Programme
)