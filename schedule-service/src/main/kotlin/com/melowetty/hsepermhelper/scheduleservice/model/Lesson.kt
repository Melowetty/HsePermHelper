package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long?,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "subject_id")
    val subject: Subject,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "group_id")
    val group: Group,
    val subGroup: Int?,
    val date: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val lecturer: String?,
    @OneToMany(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "lesson_id")
    val places: List<LessonPlace>?,
    @ElementCollection
    val links: List<String>?,
    @ElementCollection
    val additionalInfo: List<String>?,
    val lessonType: LessonType,
)