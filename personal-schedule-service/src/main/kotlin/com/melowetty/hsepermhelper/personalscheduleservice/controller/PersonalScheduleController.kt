package com.melowetty.hsepermhelper.personalscheduleservice.controller

import com.melowetty.hsepermhelper.personalscheduleservice.model.CommonSchedule
import com.melowetty.hsepermhelper.personalscheduleservice.service.ScheduleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonalScheduleController(
    private val scheduleService: ScheduleService,
) {
    @GetMapping("personal-schedules")
    fun getPersonalSchedule(): List<CommonSchedule> {
        return scheduleService.getScheduleByGroupId()
    }
}