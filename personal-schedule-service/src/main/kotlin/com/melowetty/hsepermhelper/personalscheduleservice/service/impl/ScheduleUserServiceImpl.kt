package com.melowetty.hsepermhelper.personalscheduleservice.service.impl

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto
import com.melowetty.hsepermhelper.personalscheduleservice.mapper.ScheduleUserMapper
import com.melowetty.hsepermhelper.personalscheduleservice.repository.ScheduleUserRepository
import com.melowetty.hsepermhelper.personalscheduleservice.service.ScheduleUserService
import org.springframework.stereotype.Service

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
}