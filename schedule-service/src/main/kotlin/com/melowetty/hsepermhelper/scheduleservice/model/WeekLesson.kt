package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.Entity
import java.time.LocalDate
import java.time.LocalTime

@Entity
class WeekLesson(
    id: Long?,
    subject: Subject,
    group: Group,
    subGroup: Int?,
    val date: LocalDate,
    startTime: LocalTime,
    endTime: LocalTime,
    lecturer: String?,
    places: List<LessonPlace>?,
    links: List<String>?,
    additionalInfo: List<String>?,
    lessonType: LessonType,
): BaseLesson(
    id = id,
    subject = subject,
    group = group,
    subGroup = subGroup,
    startTime = startTime,
    endTime = endTime,
    lecturer = lecturer,
    places = places,
    links = links,
    additionalInfo = additionalInfo,
    lessonType = lessonType,
)