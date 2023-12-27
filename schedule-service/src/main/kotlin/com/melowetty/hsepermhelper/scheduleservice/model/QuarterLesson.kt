package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.Entity
import java.time.DayOfWeek
import java.time.LocalTime

@Entity
class QuarterLesson(
    id: Long?,
    subject: Subject,
    group: Group,
    subGroup: Int?,
    val dayOfWeek: DayOfWeek,
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