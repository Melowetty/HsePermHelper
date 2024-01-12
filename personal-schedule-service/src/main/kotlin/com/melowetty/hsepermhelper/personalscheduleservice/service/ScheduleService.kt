package com.melowetty.hsepermhelper.personalscheduleservice.service

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleFilter
import com.melowetty.hsepermhelper.personalscheduleservice.model.CommonSchedule
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "schedule-service")
interface ScheduleService {
    @GetMapping("schedules")
    fun getScheduleByGroupId(): List<CommonSchedule>
    @GetMapping("schedules")
    fun getUserSchedule(@RequestBody scheduleFilter: ScheduleFilter): Map<String, Any>
}