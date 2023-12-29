package com.melowetty.hsepermhelper.scheduleservice.mapper.en

import com.ibm.icu.text.Transliterator
import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.model.LessonPlace
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
            office = entity.office?.let { translate(it) },
            building = entity.building
        )
    }

    override fun getLanguage(): Language {
        return Language.ENGLISH
    }

    companion object {
        private const val CYRILLIC_TO_LATIN = "Russian-Latin/BGN"
        private fun translate(russian: String): String {
            return Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(russian)
        }
    }
}