package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.MapperWithLanguage
import com.melowetty.hsepermhelper.scheduleservice.mapper.ProgrammeMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.hsepermhelper.scheduleservice.repository.ProgrammeRepository
import com.melowetty.hsepermhelper.scheduleservice.service.ProgrammeService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProgrammeServiceImpl(
    private val programmeRepository: ProgrammeRepository,
    programmeMappers: List<ProgrammeMapper>,
    @Qualifier("programme_ru_mapper")
    private val programmeRuMapper: ProgrammeMapper
): ProgrammeService, MapperWithLanguage<ProgrammeMapper>(programmeMappers, programmeRuMapper) {
    @Transactional
    override fun save(programme: Programme): Programme {
        return programmeRepository.findByNameAndCourse(programme.name, programme.course)
            ?: programmeRepository.save(programme
        )
    }

    override fun getAllProgramsByCourse(course: Int, lang: Language): List<ProgrammeDto> {
        return programmeRepository.findAllByCourse(course).map {
            getMapper(lang).toDto(it)
        }
    }
}