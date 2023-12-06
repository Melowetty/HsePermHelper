package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.Schedule
import org.springframework.stereotype.Component

class ScheduleMapper(
    private val lessonMapper: LessonMapper
) {
    fun toEntity(schedule: ScheduleDto): Schedule {
        return Schedule(
            uuid = schedule.uuid,
            weekNumber = schedule.weekNumber,
            lessons = schedule.lessons.map { lessonMapper.toEntity(it) },
            weekStart = schedule.weekStart,
            weekEnd = schedule.weekEnd,
            scheduleType = schedule.scheduleType,
        )
    }

    fun toDto(schedule: Schedule): ScheduleDto {
        return ScheduleDto(
            uuid = schedule.uuid,
            weekNumber = schedule.weekNumber,
            lessons = schedule.lessons.map { lessonMapper.toDto(schedule.scheduleType, it) },
            weekStart = schedule.weekStart,
            weekEnd = schedule.weekEnd,
            scheduleType = schedule.scheduleType
        )
    }
}