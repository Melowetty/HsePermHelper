package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*

@Entity
@Table(
    name = "education_group",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["display_name", "programme_id"]),
    ]
)
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long?,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "programme_id")
    val programme: Programme,
    val displayName: String,
    val translatedDisplayName: String?
)