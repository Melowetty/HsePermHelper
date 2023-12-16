package com.melowetty.hsepermhelper.scheduleservice.controller

import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class ScheduleController(
    private val scheduleService: ScheduleService
) {
    @GetMapping("schedules")
    fun getAllSchedules() =
        scheduleService.getSchedulesByGroupId(2, Language.ENGLISH)

    @PostMapping("schedules")
    fun convert(@RequestBody bytes: String) =
        scheduleService.mockInputStream(bytes)
}