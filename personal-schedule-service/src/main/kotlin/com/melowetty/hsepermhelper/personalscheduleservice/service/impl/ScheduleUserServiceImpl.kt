package com.melowetty.hsepermhelper.personalscheduleservice.service.impl

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto
import com.melowetty.hsepermhelper.personalscheduleservice.mapper.ScheduleUserMapper
import com.melowetty.hsepermhelper.personalscheduleservice.repository.ScheduleUserRepository
import com.melowetty.hsepermhelper.personalscheduleservice.service.ScheduleUserService
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class ScheduleUserServiceImpl(
    private val scheduleUserRepository: ScheduleUserRepository,
    private val scheduleUserMapper: ScheduleUserMapper
): ScheduleUserService {
    override fun getAllScheduleUsers(): List<ScheduleUserDto> {
        return scheduleUserRepository.findAll().map { scheduleUserMapper.toDto(it) }
    }

    override fun createScheduleUser(user: ScheduleUserDto) {
        val userAsEntity = scheduleUserMapper.toEntity(user)
        scheduleUserRepository.save(userAsEntity)
    }

    override fun getScheduleUser(uuid: UUID): ScheduleUserDto? {
        return scheduleUserRepository.findById(uuid).getOrNull()?.let { scheduleUserMapper.toDto(it) }
    }

    override fun addHiddenSubjectsForUser(user: ScheduleUserDto, hiddenSubjects: List<Long>) {
        val newHiddenSubjects = user.settings.hiddenSubjects.toMutableSet()
        newHiddenSubjects.addAll(hiddenSubjects)
        val newSettings = user.settings.copy(hiddenSubjects = newHiddenSubjects)
        val newUser = user.copy(settings = newSettings)
        scheduleUserRepository.save(scheduleUserMapper.toEntity(newUser))
    }

    override fun deleteHiddenSubjectsForUser(user: ScheduleUserDto, hiddenSubjects: List<Long>) {
        val newHiddenSubjects = user.settings.hiddenSubjects.toMutableSet()
        newHiddenSubjects.removeAll(hiddenSubjects.toSet())
        val newSettings = user.settings.copy(hiddenSubjects = newHiddenSubjects)
        val newUser = user.copy(settings = newSettings)
        scheduleUserRepository.save(scheduleUserMapper.toEntity(newUser))
    }
}