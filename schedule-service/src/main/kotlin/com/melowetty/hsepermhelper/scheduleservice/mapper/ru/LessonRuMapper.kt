package com.melowetty.hsepermhelper.scheduleservice.mapper.ru

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.*
import com.melowetty.hsepermhelper.scheduleservice.utils.DateUtils
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component("lesson_ru_mapper")
class LessonRuMapper(
    private val lessonPlaceMapper: LessonPlaceMapper,
): LessonMapper {
    override fun toEntity(lesson: LessonDto): Lesson {
        return Lesson(
            id = null,
            subject = Subject(
                id = null,
                name = lesson.subject,
                translatedName = null,
                course = lesson.course,
                group = Group(
                    id = null,
                    programme = Programme(
                        id = null,
                        name = lesson.programme,
                        translatedName = null,
                        course = lesson.course
                    ),
                    displayName = lesson.group,
                    translatedDisplayName = null,
                )
            ),
            subGroup = lesson.subGroup,
            date = lesson.date,
            startTime = LocalTime.parse(lesson.startTimeStr, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)),
            endTime = LocalTime.parse(lesson.endTimeStr, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN)),
            lecturer = lesson.lecturer,
            places = lesson.places?.map { lessonPlaceMapper.toEntity(it) },
            lessonType = lesson.lessonType,
        )
    }

    override fun toDto(scheduleType: ScheduleType, lesson: Lesson): LessonDto {
        return LessonDto(
            subject = lesson.subject.name,
            course = lesson.subject.course,
            programme = lesson.subject.group.programme.name,
            group = lesson.subject.group.displayName,
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