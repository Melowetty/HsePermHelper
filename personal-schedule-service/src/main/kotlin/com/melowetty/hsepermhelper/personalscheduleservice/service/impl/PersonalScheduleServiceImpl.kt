package com.melowetty.hsepermhelper.personalscheduleservice.service.impl

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleFilter
import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto
import com.melowetty.hsepermhelper.personalscheduleservice.exceptions.NeedsSelectSubgroupForSubjectsException
import com.melowetty.hsepermhelper.personalscheduleservice.service.PersonalScheduleService
import com.melowetty.hsepermhelper.personalscheduleservice.service.ScheduleService
import org.springframework.stereotype.Service

@Service
class PersonalScheduleServiceImpl(
    private val scheduleService: ScheduleService
): PersonalScheduleService {
    override fun getPersonalSchedule(user: ScheduleUserDto): Map<String, Any> {
        if(user.settings.subGroupSelect.any { it.value == null }) throw NeedsSelectSubgroupForSubjectsException()
        val scheduleFilter = ScheduleFilter(
            groupId = user.settings.groupId,
            baseSubGroup = user.settings.baseSubGroup,
            subgroupSelects = user.settings.subGroupSelect.mapValues { it.value!! },
            hiddenSubjects = user.settings.hiddenSubjects,
        )
        return scheduleService.getUserSchedule(scheduleFilter)
    }
}