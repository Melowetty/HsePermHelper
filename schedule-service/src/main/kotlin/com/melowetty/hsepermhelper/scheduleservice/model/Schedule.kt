package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.time.LocalDate
import java.util.*

@Entity
data class Schedule(
    @Id
    val uuid: UUID,
    val weekNumber: Int?,
    @OneToMany
    @JoinColumn(name = "schedule_id")
    val lessons: List<Lesson>,
    val weekStart: LocalDate,
    val weekEnd: LocalDate,
    val scheduleType: ScheduleType,
)