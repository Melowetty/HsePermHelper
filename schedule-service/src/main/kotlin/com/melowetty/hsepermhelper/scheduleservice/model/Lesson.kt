package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long?,
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    val subject: Subject,
    val subGroup: Int?,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val lecturer: String?,
    @OneToMany
    @JoinColumn(name = "lesson_id")
    val places: List<LessonPlace>? = null,
    @ElementCollection
    val links: List<String>? = null,
    @ElementCollection
    val additionalInfo: List<String>? = null,
    val lessonType: LessonType,
)