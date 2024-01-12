package com.melowetty.hsepermhelper.personalscheduleservice.service

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto

interface PersonalScheduleService {
    fun getPersonalSchedule(user: ScheduleUserDto): Map<String, Any>
}