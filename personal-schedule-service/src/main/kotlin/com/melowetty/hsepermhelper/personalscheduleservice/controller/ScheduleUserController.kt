package com.melowetty.hsepermhelper.personalscheduleservice.controller

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto
import com.melowetty.hsepermhelper.personalscheduleservice.service.ScheduleUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("users")
@RestController
class ScheduleUserController(
    private val scheduleUserService: ScheduleUserService
) {
    @GetMapping
    fun getAllScheduleUsers(): List<ScheduleUserDto> {
        return scheduleUserService.getAllScheduleUsers()
    }

    @PostMapping
    fun createScheduleUser(@RequestBody scheduleUserDto: ScheduleUserDto) {
        scheduleUserService.createScheduleUser(scheduleUserDto)
    }
}