package com.melowetty.hsepermhelper.scheduleservice.mapper.en

import com.ibm.icu.text.Transliterator
import com.melowetty.hsepermhelper.scheduleservice.dto.BaseLessonDto
import com.melowetty.hsepermhelper.scheduleservice.dto.QuarterLessonDto
import com.melowetty.hsepermhelper.scheduleservice.dto.WeekLessonDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.*
import com.melowetty.hsepermhelper.scheduleservice.utils.DateUtils
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component("lesson_en_mapper")
class LessonEnMapper(
    @Qualifier("lesson_place_en_mapper")
    private val lessonPlaceMapper: LessonPlaceMapper,
): LessonMapper {
    override fun toEntity(lesson: BaseLessonDto): BaseLesson {
        val programme = Programme(
            id = null,
            name = lesson.programme,
            translatedName = lesson.programme,
            course = lesson.course,
            fullName = null,
            translatedFullName = null,
        )
        val subject = Subject(
            name = lesson.subject,
            translatedName = lesson.subject,
            programme = programme,
        )
        val group = Group(
            id = null,
            programme = programme,
            displayName = lesson.group,
            translatedDisplayName = lesson.group,
        )
        val startTime = LocalTime.parse(lesson.startTime, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
        val endTime = LocalTime.parse(lesson.endTime, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
        val places = lesson.places?.map { lessonPlaceMapper.toEntity(it) }
        when (lesson) {
            is WeekLessonDto -> {
                return WeekLesson(
                    id = null,
                    subject = subject,
                    group = group,
                    subGroup = lesson.subGroup,
                    date = lesson.date,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = lesson.lecturer,
                    places = places,
                    links = lesson.links,
                    additionalInfo = lesson.additionalInfo,
                    lessonType = lesson.lessonType,
                )
            }
            is QuarterLessonDto -> {
                return QuarterLesson(
                    id = null,
                    subject = subject,
                    group = group,
                    subGroup = lesson.subGroup,
                    dayOfWeek = lesson.dayOfWeek,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = lesson.lecturer,
                    places = places,
                    links = lesson.links,
                    additionalInfo = lesson.additionalInfo,
                    lessonType = lesson.lessonType,
                )
            }
            else -> throw IllegalArgumentException("Неверный тип у пары!")
        }
    }

    override fun toDto(scheduleType: ScheduleType, lesson: BaseLesson): BaseLessonDto {
        val subject = lesson.subject.translatedName ?: translate(lesson.subject.name)
        val course = lesson.subject.programme.course
        val programme = lesson.subject.programme.translatedName ?: translate(lesson.subject.programme.name)
        val group = lesson.group.translatedDisplayName ?: translate(lesson.group.displayName)
        val startTime = lesson.startTime.format(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
        val endTime = lesson.endTime.format(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
        val lecturer = if (lesson.lecturer != null) translate(lesson.lecturer) else null
        var places = lesson.places?.map { lessonPlaceMapper.toDto(it) }
        if (places?.isEmpty() == true) {
            places = null
        }
        when (lesson) {
            is WeekLesson -> {
                return WeekLessonDto(
                    subject = subject,
                    course = course,
                    programme = programme,
                    group = group,
                    subGroup = lesson.subGroup,
                    date = lesson.date,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = lecturer,
                    places = places,
                    lessonType = lesson.lessonType,
                    parentScheduleType = scheduleType,
                )
            }
            is QuarterLesson -> {
                return QuarterLessonDto(
                    subject = subject,
                    course = course,
                    programme = programme,
                    group = group,
                    subGroup = lesson.subGroup,
                    dayOfWeek = lesson.dayOfWeek,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = lecturer,
                    places = places,
                    lessonType = lesson.lessonType,
                    parentScheduleType = scheduleType,
                )
            }
            else -> throw IllegalArgumentException("Неверный тип у пары!")
        }
    }
    companion object {
        private const val CYRILLIC_TO_LATIN = "Russian-Latin/BGN"
        private fun translate(russian: String): String {
            return Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(russian)
        }
    }

    override fun getLanguage(): Language {
        return Language.ENGLISH
    }
}