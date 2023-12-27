package com.melowetty.hsepermhelper.scheduleservice.controller

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.service.LanguageService
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class ScheduleController(
    private val scheduleService: ScheduleService,
    private val languageService: LanguageService,
) {

    @GetMapping("schedules")
    fun getAllSchedulesByGroupId(
        request: HttpServletRequest,
        @RequestParam(required = false) programmeId: Long?,
        @RequestParam(required = false) groupId: Long?
    ): List<BaseScheduleDto> {
        val lang = languageService.getLanguageFromHeaders(request)
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