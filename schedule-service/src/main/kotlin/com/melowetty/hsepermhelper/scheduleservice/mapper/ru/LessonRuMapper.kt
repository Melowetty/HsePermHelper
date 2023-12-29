package com.melowetty.hsepermhelper.scheduleservice.mapper.ru

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

@Component("lesson_ru_mapper")
class LessonRuMapper(
    @Qualifier("lesson_place_ru_mapper")
    private val lessonPlaceMapper: LessonPlaceMapper,
): LessonMapper {
    override fun toEntity(dto: BaseLessonDto): BaseLesson {
        val programme = Programme(
            id = null,
            name = dto.programme,
            translatedName = null,
            course = dto.course,
            fullName = null,
            translatedFullName = null,
        )
        val subject = Subject(
            name = dto.subject,
            translatedName = null,
            programme = programme,
        )
        val group = Group(
            id = null,
            programme = programme,
            displayName = dto.group,
            translatedDisplayName = null,
        )
        val startTime = LocalTime.parse(dto.startTime, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
        val endTime = LocalTime.parse(dto.endTime, DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
        val places = dto.places?.map { lessonPlaceMapper.toEntity(it) }
        when (dto) {
            is WeekLessonDto -> {
                return WeekLesson(
                    id = null,
                    subject = subject,
                    group = group,
                    subGroup = dto.subGroup,
                    date = dto.date,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = dto.lecturer,
                    places = places,
                    links = dto.links,
                    additionalInfo = dto.additionalInfo,
                    lessonType = dto.lessonType,
                )
            }
            is QuarterLessonDto -> {
                return QuarterLesson(
                    id = null,
                    subject = subject,
                    group = group,
                    subGroup = dto.subGroup,
                    dayOfWeek = dto.dayOfWeek,
                    startTime = startTime,
                    endTime = endTime,
                    lecturer = dto.lecturer,
                    places = places,
                    links = dto.links,
                    additionalInfo = dto.additionalInfo,
                    lessonType = dto.lessonType,
                )
            }
            else -> throw IllegalArgumentException("Неверный тип у пары!")
        }
    }

    override fun toDto(scheduleType: ScheduleType, lesson: BaseLesson): BaseLessonDto {
        val subject = lesson.subject.name
        val course = lesson.subject.programme.course
        val programme = lesson.subject.programme.name
        val group = lesson.group.displayName
        val startTime = lesson.startTime.format(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
        val endTime = lesson.endTime.format(DateTimeFormatter.ofPattern(DateUtils.TIME_PATTERN))
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
                    lecturer = lesson.lecturer,
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
                    lecturer = lesson.lecturer,
                    places = places,
                    lessonType = lesson.lessonType,
                    parentScheduleType = scheduleType,
                )
            }
            else -> throw IllegalArgumentException("Неверный тип у пары!")
        }
    }

    override fun toDto(entity: BaseLesson): BaseLessonDto {
        return toDto(ScheduleType.COMMON_WEEK_SCHEDULE, entity)
    }

    override fun getLanguage(): Language {
        return Language.RUSSIAN
    }
}