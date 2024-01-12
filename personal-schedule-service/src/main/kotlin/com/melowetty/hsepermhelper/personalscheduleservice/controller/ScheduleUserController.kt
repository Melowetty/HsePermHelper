package com.melowetty.hsepermhelper.personalscheduleservice.controller

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto
import com.melowetty.hsepermhelper.personalscheduleservice.exceptions.UserNotFoundException
import com.melowetty.hsepermhelper.personalscheduleservice.service.PersonalScheduleService
import com.melowetty.hsepermhelper.personalscheduleservice.service.ScheduleUserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("users")
@RestController
class ScheduleUserController(
    private val scheduleUserService: ScheduleUserService,
    private val personalScheduleService: PersonalScheduleService
) {
    @GetMapping
    fun getAllScheduleUsers(): List<ScheduleUserDto> {
        return scheduleUserService.getAllScheduleUsers()
    }

    @GetMapping("{uuid}")
    fun getScheduleUser(@PathVariable(name = "uuid") uuid: UUID): ScheduleUserDto {
        return scheduleUserService.getScheduleUser(uuid) ?: throw UserNotFoundException("User not found by UUID")
    }

    @GetMapping("{uuid}/schedules")
    fun getPersonalSchedule(@PathVariable(name = "uuid") uuid: UUID): Map<String, Any> {
        val user = scheduleUserService.getScheduleUser(uuid) ?: throw UserNotFoundException("User not found by UUID")
        return personalScheduleService.getPersonalSchedule(user)
    }

    @GetMapping("{uuid}/hidden-subjects")
    fun getScheduleUserHiddenSubjects(@PathVariable(name = "uuid") uuid: UUID): List<Long> {
        val user = scheduleUserService.getScheduleUser(uuid) ?: throw UserNotFoundException("User not found by UUID")
        return user.settings.hiddenSubjects.toList()
    }

    @PostMapping("{uuid}/hidden-subjects")
    fun addScheduleUserHiddenSubjects(@PathVariable(name = "uuid") uuid: UUID, @RequestBody subjectIds: List<Long>) {
        val user = scheduleUserService.getScheduleUser(uuid) ?: throw UserNotFoundException("User not found by UUID")
        scheduleUserService.addHiddenSubjectsForUser(user, subjectIds)
    }

    @DeleteMapping("{uuid}/hidden-subjects")
    fun deleteScheduleUserHiddenSubjects(@PathVariable(name = "uuid") uuid: UUID, @RequestBody subjectIds: List<Long>) {
        val user = scheduleUserService.getScheduleUser(uuid) ?: throw UserNotFoundException("User not found by UUID")
        //scheduleUserService.addHiddenSubjectsForUser(user, subjectIds)
    }

    @GetMapping("{uuid}/subgroup-selections")
    fun getScheduleUserSubgroupSelections(@PathVariable(name = "uuid") uuid: UUID): Map<Long, Int?> {
        val user = scheduleUserService.getScheduleUser(uuid) ?: throw UserNotFoundException("User not found by UUID")
        return user.settings.subGroupSelect
    }

    @PostMapping
    fun createScheduleUser(@RequestBody scheduleUserDto: ScheduleUserDto) {
        scheduleUserService.createScheduleUser(scheduleUserDto)
    }
}