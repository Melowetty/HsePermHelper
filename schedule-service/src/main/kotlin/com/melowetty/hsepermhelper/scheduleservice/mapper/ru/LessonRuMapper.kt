package com.melowetty.hsepermhelper.scheduleservice.mapper.ru

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.*
import com.melowetty.hsepermhelper.scheduleservice.utils.DateUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component("lesson_ru_mapper")
class LessonRuMapper(
    @Qualifier("lesson_place_ru_mapper")
    private val lessonPlaceMapper: LessonPlaceMapper,
): LessonMapper {
    override fun toEntity(lesson: LessonDto): Lesson {
        val programme = Programme(
            id = null,
            name = lesson.programme,
            translatedName = null,
            course = lesson.course,
            fullName = null,
            translatedFullName = null,
        )
        return Lesson(
            id = null,
            subject = Subject(
                name = lesson.subject,
                translatedName = null,
                programme = programme,
            ),
            group = Group(
                id = null,
                programme = programme,
                displayName = lesson.group,
                translatedDisplayName = null,
            ),
            subGroup = lesson.subGroup,
            date = lesson.date,
            startTime = LocalTime.parse(lesson.startTimeStr, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)),
            endTime = LocalTime.parse(lesson.endTimeStr, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)),
            lecturer = lesson.lecturer,
            places = lesson.places?.map { lessonPlaceMapper.toEntity(it) },
            links = lesson.links,
            additionalInfo = lesson.additionalInfo,
            lessonType = lesson.lessonType,
        )
    }

    override fun toDto(scheduleType: ScheduleType, lesson: Lesson): LessonDto {
        return LessonDto(
            subject = lesson.subject.name,
            course = lesson.subject.programme.course,
            programme = lesson.subject.programme.name,
            group = lesson.group.displayName,
            subGroup = lesson.subGroup,
            date = lesson.date,
            startTimeStr = lesson.startTime.format(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)),
            endTimeStr = lesson.endTime.format(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)),
            lecturer = lesson.lecturer,
            places = lesson.places?.map { lessonPlaceMapper.toDto(it) },
            lessonType = lesson.lessonType,
            parentScheduleType = scheduleType,
        )
    }
}