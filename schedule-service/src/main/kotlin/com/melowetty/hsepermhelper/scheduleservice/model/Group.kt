package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*

@Entity
@Table(
    name = "education_group",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["displayName"]),
        //UniqueConstraint(columnNames = ["translatedDisplayName"])
    ]
)
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long?,
    @ManyToOne
    @JoinColumn(name = "education_group_id")
    val programme: Programme,
    val displayName: String,
    val translatedDisplayName: String?
)