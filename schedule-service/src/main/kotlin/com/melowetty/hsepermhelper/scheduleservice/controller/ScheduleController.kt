package com.melowetty.hsepermhelper.scheduleservice.controller

import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import com.melowetty.hsepermhelper.scheduleservice.utils.LanguageUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class ScheduleController(
    private val scheduleService: ScheduleService
) {

    @GetMapping("schedules")
    fun getAllSchedulesByGroupId(
        request: HttpServletRequest,
        @RequestParam(required = false) programmeId: Long?,
        @RequestParam(required = false) groupId: Long?
    ): List<ScheduleDto> {
        val lang = LanguageUtils.getLanguageFromHeaders(request)
        return if(programmeId != null) {
            if(groupId != null) {
                scheduleService.getSchedulesByGroupId(groupId, lang)
            } else {
                scheduleService.getSchedulesByProgrammeId(programmeId, lang)
            }
        } else {
            if(groupId != null) {
                scheduleService.getSchedulesByGroupId(groupId, lang)
            } else {
                scheduleService.findAllSchedules(lang)
            }
        }
    }

    @PostMapping("schedules")
    fun convert(@RequestBody bytes: String) =
        scheduleService.mockInputStream(bytes)
}