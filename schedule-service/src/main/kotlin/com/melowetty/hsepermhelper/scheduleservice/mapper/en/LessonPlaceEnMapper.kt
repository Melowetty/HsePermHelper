package com.melowetty.hsepermhelper.scheduleservice.mapper.en

import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.model.LessonPlace
import com.melowetty.hsepermhelper.scheduleservice.utils.TranslateUtils
import org.springframework.stereotype.Component

@Component("lesson_place_en_mapper")
class LessonPlaceEnMapper: LessonPlaceMapper {
    override fun toEntity(dto: LessonPlaceDto): LessonPlace {
        return LessonPlace(
            building = dto.building,
            office = dto.office,
        )
    }

    override fun toDto(entity: LessonPlace): LessonPlaceDto {
        return LessonPlaceDto(
            office = entity.office?.let { TranslateUtils.translate(it) },
            building = entity.building
        )
    }

    override fun getLanguage(): Language {
        return Language.ENGLISH
    }
}