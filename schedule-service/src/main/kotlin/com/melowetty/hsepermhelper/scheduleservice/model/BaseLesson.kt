package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.*
import java.time.LocalTime

@Entity
@Table(name = "lesson")
@Inheritance
abstract class BaseLesson(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    val id: Long?,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "subject_id")
    var subject: Subject,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "group_id")
    var group: Group,
    val subGroup: Int?,
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