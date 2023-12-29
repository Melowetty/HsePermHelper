package com.melowetty.hsepermhelper.scheduleservice.mapper.en

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.ProgrammeMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.hsepermhelper.scheduleservice.utils.TranslateUtils
import org.springframework.stereotype.Component

@Component("programme_en_mapper")
class ProgrammeEnMapper: ProgrammeMapper {
    override fun toDto(entity: Programme): ProgrammeDto {
        return ProgrammeDto(
            id = entity.id,
            name = TranslateUtils.translate(entity.name),
            fullName = entity.fullName?.let { TranslateUtils.translate(it) }
        )
    }

    override fun getLanguage(): Language {
        return Language.ENGLISH
    }
}