package com.melowetty.hsepermhelper.scheduleservice.controller

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.dto.LessonsFilter
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import com.melowetty.languagessupportlibrary.utils.LanguageUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("schedules")
class ScheduleController(
    private val scheduleService: ScheduleService,
) {

//    @GetMapping
//    fun getAllSchedulesByGroupId(
//        @RequestHeader headers: Map<String, String>,
//        @RequestParam(required = false) programmeId: Long?,
//        @RequestParam(required = false) groupId: Long?
//    ): List<BaseScheduleDto> {
//        val lang = LanguageUtils.getLanguageFromHeaders(headers)
//        return if(programmeId != null) {
//            if(groupId != null) {
//                scheduleService.getSchedulesByGroupId(groupId, lang)
//            } else {
//                scheduleService.getSchedulesByProgrammeId(programmeId, lang)
//            }
//        } else {
//            if(groupId != null) {
//                scheduleService.getSchedulesByGroupId(groupId, lang)
//            } else {
//                scheduleService.findAllSchedules(lang)
//            }
//        }
//    }

    @GetMapping
    fun getAllSchedulesFilteredByUserSettings(
        @RequestHeader headers: Map<String, String>,
        @RequestBody settings: LessonsFilter
    ): List<BaseScheduleDto> {
        val lang = LanguageUtils.getLanguageFromHeaders(headers)
        return scheduleService.filterSchedulesByLessonsFilter(lang, settings)
    }

    @PostMapping("schedules")
    fun convert(@RequestBody bytes: String) =
        scheduleService.mockInputStream(bytes)
}