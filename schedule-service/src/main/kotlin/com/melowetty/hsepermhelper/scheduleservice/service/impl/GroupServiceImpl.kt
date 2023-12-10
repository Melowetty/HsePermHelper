package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.model.Group
import com.melowetty.hsepermhelper.scheduleservice.repository.GroupRepository
import com.melowetty.hsepermhelper.scheduleservice.service.GroupService
import com.melowetty.hsepermhelper.scheduleservice.service.ProgrammeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GroupServiceImpl(
    private val groupRepository: GroupRepository,
    private val programmeService: ProgrammeService,
): GroupService {
    @Transactional
    override fun save(group: Group): Group {
        val programme = programmeService.save(group.programme)
        return groupRepository.findByProgrammeAndDisplayName(programme, group.displayName)
            ?: groupRepository.save(group.copy(programme = programme))
    }
}