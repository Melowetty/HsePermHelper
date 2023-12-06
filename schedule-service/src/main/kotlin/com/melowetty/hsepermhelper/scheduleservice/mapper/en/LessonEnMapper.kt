package com.melowetty.hsepermhelper.scheduleservice.mapper.en

import com.ibm.icu.text.Transliterator
import com.melowetty.hsepermhelper.scheduleservice.dto.LessonDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.*
import com.melowetty.hsepermhelper.scheduleservice.utils.DateUtils
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component("lesson_en_mapper")
class LessonEnMapper(
    private val lessonPlaceMapper: LessonPlaceMapper,
): LessonMapper {
    override fun toEntity(lesson: LessonDto): Lesson {
        return Lesson(
            id = null,
            subject = Subject(
                id = null,
                name = lesson.subject,
                translatedName = lesson.subject,
                course = lesson.course,
                group = Group(
                    id = null,
                    programme = Programme(
                        id = null,
                        name = lesson.programme,
                        translatedName = lesson.programme,
                        course = lesson.course
                    ),
                    displayName = lesson.group,
                    translatedDisplayName = lesson.group,
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
            programme = lesson.subject.group.programme.translatedName ?: translate(lesson.subject.group.programme.name),
            group = lesson.subject.group.translatedDisplayName ?: translate(lesson.subject.group.displayName),
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
    companion object {
        private const val CYRILLIC_TO_LATIN = "Russian-Latin/BGN"
        private fun translate(russian: String): String {
            return Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(russian)
        }
    }
}