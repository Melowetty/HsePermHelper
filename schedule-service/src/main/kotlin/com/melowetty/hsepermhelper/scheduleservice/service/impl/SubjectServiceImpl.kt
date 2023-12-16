package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.model.Subject
import com.melowetty.hsepermhelper.scheduleservice.repository.SubjectRepository
import com.melowetty.hsepermhelper.scheduleservice.service.ProgrammeService
import com.melowetty.hsepermhelper.scheduleservice.service.SubjectService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubjectServiceImpl(
    private val subjectRepository: SubjectRepository,
    private val programmeService: ProgrammeService
): SubjectService {
    @Transactional
    override fun save(subject: Subject): Subject {
        val programme = programmeService.save(subject.programme)
        return subjectRepository.findByProgrammeAndName(programme, subject.name)
            ?: subjectRepository.save(subject.copy(programme = programme))
    }
}