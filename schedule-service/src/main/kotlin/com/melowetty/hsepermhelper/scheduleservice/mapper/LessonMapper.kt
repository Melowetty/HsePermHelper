package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseLessonDto
import com.melowetty.hsepermhelper.scheduleservice.model.BaseLesson
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType

interface LessonMapper : Translatable {
    fun toEntity(lesson: BaseLessonDto): BaseLesson
    fun toDto(scheduleType: ScheduleType, lesson: BaseLesson): BaseLessonDto
}