package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
data class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
    @Column(name = "system_id", unique = true, nullable = false)
    val systemId: UUID ,
    val weekNumber: Int?,
    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "schedule_id")
    val lessons: List<Lesson>,
    val weekStart: LocalDate,
    val weekEnd: LocalDate,
    val scheduleType: ScheduleType,
)