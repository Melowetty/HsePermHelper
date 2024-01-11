package com.melowetty.hsepermhelper.personalscheduleservice.service

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto

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
}