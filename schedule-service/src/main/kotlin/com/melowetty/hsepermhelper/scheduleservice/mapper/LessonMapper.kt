package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonDto
import com.melowetty.hsepermhelper.scheduleservice.model.Lesson
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType

interface LessonMapper {
    fun toEntity(lesson: LessonDto): Lesson
    fun toDto(scheduleType: ScheduleType, lesson: Lesson): LessonDto
}