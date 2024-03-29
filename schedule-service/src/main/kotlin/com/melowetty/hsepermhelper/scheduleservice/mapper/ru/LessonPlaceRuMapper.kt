package com.melowetty.hsepermhelper.scheduleservice.mapper.ru

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.LessonPlace
import com.melowetty.languagessupportlibrary.model.Language
import org.springframework.stereotype.Component

@Component("lesson_place_ru_mapper")
class LessonPlaceRuMapper: LessonPlaceMapper {
    override fun toEntity(dto: LessonPlaceDto): LessonPlace {
        return LessonPlace(
            building = dto.building,
            office = dto.office,
        )
    }

    override fun toDto(entity: LessonPlace): LessonPlaceDto {
        return LessonPlaceDto(
            office = entity.office,
            building = entity.building
        )
    }

    override fun getCompatibleLanguage(): Language {
        return Language.RUSSIAN
    }
}