package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.hsepermhelper.scheduleservice.repository.ProgrammeRepository
import com.melowetty.hsepermhelper.scheduleservice.service.ProgrammeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProgrammeServiceImpl(
    private val programmeRepository: ProgrammeRepository
): ProgrammeService {
    @Transactional
    override fun save(programme: Programme): Programme {
        return programmeRepository.findByNameAndCourse(programme.name, programme.course)
            ?: programmeRepository.save(programme
        )
    }
}