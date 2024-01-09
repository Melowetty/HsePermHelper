package com.melowetty.hsepermhelper.scheduleservice.mapper.ru

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.ProgrammeMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.languagessupportlibrary.model.Language
import org.springframework.stereotype.Component

@Component("programme_ru_mapper")
class ProgrammeRuMapper: ProgrammeMapper {
    override fun toDto(entity: Programme): ProgrammeDto {
        return ProgrammeDto(
            id = entity.id,
            name = entity.name,
            fullName = entity.fullName
        )
    }

    override fun getLanguage(): Language {
        return Language.RUSSIAN
    }
}