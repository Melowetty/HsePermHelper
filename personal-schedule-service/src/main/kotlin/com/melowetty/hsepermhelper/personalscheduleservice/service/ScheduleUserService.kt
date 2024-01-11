package com.melowetty.hsepermhelper.personalscheduleservice.service

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto
import java.util.UUID

interface ScheduleUserService {
    /**
     * Get all schedule users
     *
     * @return list of schedule user dto
     */
    fun getAllScheduleUsers(): List<ScheduleUserDto>

    /**
     * Create schedule user from schedule user dto
     *
     * @param user schedule user dto object
     */
    fun createScheduleUser(user: ScheduleUserDto)

    /**
     * Get schedule user by uuid
     *
     * @return schedule user dto or null
     */
    fun getScheduleUser(uuid: UUID): ScheduleUserDto?
}