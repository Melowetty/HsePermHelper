package com.melowetty.hsepermhelper.scheduleservice.mapper.en

import com.ibm.icu.text.Transliterator
import com.melowetty.hsepermhelper.scheduleservice.dto.LessonPlaceDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonPlaceMapper
import com.melowetty.hsepermhelper.scheduleservice.model.LessonPlace
import org.springframework.stereotype.Component

@Component("lesson_place_en_mapper")
class LessonPlaceEnMapper: LessonPlaceMapper {
    override fun toEntity(lessonPlace: LessonPlaceDto): LessonPlace {
        return LessonPlace(
            building = lessonPlace.building,
            office = lessonPlace.office,
        )
    }

    override fun toDto(lessonPlace: LessonPlace): LessonPlaceDto {
        return LessonPlaceDto(
            office = lessonPlace.office?.let { translate(it) },
            building = lessonPlace.building
        )
    }

    companion object {
        private const val CYRILLIC_TO_LATIN = "Russian-Latin/BGN"
        private fun translate(russian: String): String {
            return Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(russian)
        }
    }
}