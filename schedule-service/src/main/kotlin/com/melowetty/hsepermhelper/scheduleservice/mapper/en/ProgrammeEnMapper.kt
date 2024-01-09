package com.melowetty.hsepermhelper.scheduleservice.mapper.en

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.ProgrammeMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.languagessupportlibrary.model.Language
import com.melowetty.languagessupportlibrary.utils.TranslateUtils
import org.springframework.stereotype.Component

@Component("programme_en_mapper")
class ProgrammeEnMapper: ProgrammeMapper {
    override fun toDto(entity: Programme): ProgrammeDto {
        return ProgrammeDto(
            id = entity.id,
            name = TranslateUtils.translateToRussian(entity.name),
            fullName = entity.fullName?.let { TranslateUtils.translateToRussian(it) }
        )
    }

    override fun getLanguage(): Language {
        return Language.ENGLISH
    }
}