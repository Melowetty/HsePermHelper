package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.dto.LessonsFilter
import com.melowetty.languagessupportlibrary.model.Language

interface ScheduleService {
    fun findAllSchedules(lang: Language): List<BaseScheduleDto>
    fun mockInputStream(base64: String)
    fun getSchedulesByGroupId(groupId: Long, lang: Language): List<BaseScheduleDto>
    fun getSchedulesByProgrammeId(programmeId: Long, lang: Language): List<BaseScheduleDto>
    fun filterSchedulesByLessonsFilter(lang: Language, filter: LessonsFilter): List<BaseScheduleDto>
}