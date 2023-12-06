package com.melowetty.hsepermhelper.scheduleservice.controller

import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class ScheduleController(
    private val scheduleService: ScheduleService
) {
    @GetMapping("schedules")
    fun getAllSchedules() =
        scheduleService.findAllSchedules(Language.RUSSIAN)
}