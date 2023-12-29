package com.melowetty.hsepermhelper.scheduleservice.mapper.ru

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.model.LessonPlace
import org.springframework.stereotype.Component

@Component("lesson_place_ru_mapper")
class LessonPlaceRuMapper: LessonPlaceMapper {
    override fun toEntity(lessonPlace: LessonPlaceDto): LessonPlace {
        return LessonPlace(
            building = lessonPlace.building,
            office = lessonPlace.office,
        )
    }

    override fun toDto(lessonPlace: LessonPlace): LessonPlaceDto {
        return LessonPlaceDto(
            office = lessonPlace.office,
            building = lessonPlace.building
        )
    }

    override fun getLanguage(): Language {
        return Language.RUSSIAN
    }
}