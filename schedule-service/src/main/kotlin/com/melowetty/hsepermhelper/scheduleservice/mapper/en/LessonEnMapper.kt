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
        val programme = Programme(
            id = null,
            name = lesson.programme,
            translatedName = lesson.programme,
            course = lesson.course
        )
        return Lesson(
            id = null,
            subject = Subject(
                name = lesson.subject,
                translatedName = lesson.subject,
                programme = programme,
            ),
            group = Group(
                id = null,
                programme = programme,
                displayName = lesson.group,
                translatedDisplayName = lesson.group,
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
            programme = lesson.subject.programme.translatedName ?: translate(lesson.subject.programme.name),
            group = lesson.group.translatedDisplayName ?: translate(lesson.group.displayName),
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