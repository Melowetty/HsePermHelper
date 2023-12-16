package com.melowetty.hsepermhelper.personalscheduleservice.model

import java.time.LocalDate

data class CommonScheduleLesson(
    val subject: String,
    val course: Int,
    val programme: String,
    val group: String,
    val subGroup: Int?,
    val date: LocalDate,
    val startTime: String,
    val endTime: String,
    val lecturer: String?,
    val places: List<LessonPlace>? = null,
    val links: List<String>? = null,
    val additionalInfo: List<String>? = null,
    val lessonType: LessonType,
    val parentScheduleType: ScheduleType,
): Comparable<CommonScheduleLesson> {
    override fun compareTo(other: CommonScheduleLesson): Int {
        return date.compareTo(other.date)
    }

}