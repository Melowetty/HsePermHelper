package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.model.LessonPlace
import org.springframework.stereotype.Component

@Component
class LessonPlaceMapper {
    fun toEntity(lessonPlace: LessonPlaceDto): LessonPlace {
        return LessonPlace(
            building = lessonPlace.building,
            office = lessonPlace.office,
        )
    }

    fun toDto(lessonPlace: LessonPlace): LessonPlaceDto {
        return LessonPlaceDto(
            office = lessonPlace.office,
            building = lessonPlace.building
        )
    }
}