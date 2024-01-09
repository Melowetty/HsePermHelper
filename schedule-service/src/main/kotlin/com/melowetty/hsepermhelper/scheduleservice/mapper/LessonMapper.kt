package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseLessonDto
import com.melowetty.hsepermhelper.scheduleservice.model.BaseLesson
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import com.melowetty.languagessupportlibrary.model.Translatable
import com.melowetty.mapperlibrary.Mappable

interface LessonMapper : Mappable<BaseLesson, BaseLessonDto>, Translatable {
    fun toDto(scheduleType: ScheduleType, lesson: BaseLesson): BaseLessonDto
}