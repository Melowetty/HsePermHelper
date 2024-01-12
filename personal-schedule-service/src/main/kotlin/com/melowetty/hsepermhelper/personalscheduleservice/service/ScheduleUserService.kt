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

    /**
     * Add hidden subjects in user settings
     *
     * @param user target user
     * @param hiddenSubjects list of subjects id, which must be added
     */
    fun addHiddenSubjectsForUser(user: ScheduleUserDto, hiddenSubjects: List<Long>)

    /**
     * Delete hidden subjects from user settings
     *
     * @param user target user
     * @param hiddenSubjects list of subjects id, which must be deleted
     */
    fun deleteHiddenSubjectsForUser(user: ScheduleUserDto, hiddenSubjects: List<Long>)
}