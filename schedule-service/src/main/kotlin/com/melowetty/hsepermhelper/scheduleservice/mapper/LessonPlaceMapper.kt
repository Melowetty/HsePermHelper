package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.model.LessonPlace

interface LessonPlaceMapper {
    fun toEntity(lessonPlace: LessonPlaceDto): LessonPlace

    fun toDto(lessonPlace: LessonPlace): LessonPlaceDto
}