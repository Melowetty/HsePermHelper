package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "schedule")
@Inheritance
abstract class BaseSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
    @Column(name = "system_id", unique = true, nullable = false)
    val systemId: UUID ,
    @OneToMany(cascade = [CascadeType.PERSIST], targetEntity = BaseLesson::class)
    @JoinColumn(name = "schedule_id")
    var lessons: List<BaseLesson>,
    val scheduleStart: LocalDate,
    val scheduleEnd: LocalDate,
    val scheduleType: ScheduleType,
)