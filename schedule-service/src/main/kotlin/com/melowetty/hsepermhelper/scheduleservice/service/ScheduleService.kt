package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.Language

interface ScheduleService {
    fun findAllSchedules(lang: Language): List<ScheduleDto>
}