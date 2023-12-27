package com.melowetty.hsepermhelper.scheduleservice.dto

import com.melowetty.hsepermhelper.scheduleservice.model.LessonType
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import java.time.DayOfWeek

class QuarterLessonDto(
    subject: String,
    course: Int,
    programme: String,
    group: String,
    subGroup: Int?,
    val dayOfWeek: DayOfWeek,
    startTime: String,
    endTime: String,
    lecturer: String?,
    places: List<LessonPlaceDto>? = null,
    links: List<String>? = null,
    additionalInfo: List<String>? = null,
    lessonType: LessonType,
    parentScheduleType: ScheduleType,
): BaseLessonDto(
    subject = subject,
    course = course,
    programme = programme,
    group = group,
    subGroup = subGroup,
    startTime = startTime,
    endTime = endTime,
    lecturer = lecturer,
    places = places,
    links = links,
    additionalInfo = additionalInfo,
    lessonType = lessonType,
    parentScheduleType = parentScheduleType,
) {
    override fun compareTo(other: BaseLessonDto): Int {
        return if (other is QuarterLessonDto) dayOfWeek.compareTo(dayOfWeek)
        else startTime.compareTo(endTime)
    }
}