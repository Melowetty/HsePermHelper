package com.melowetty.hsepermhelper.personalscheduleservice.mapper

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleSettingsDto
import com.melowetty.hsepermhelper.personalscheduleservice.model.ScheduleSettings
import com.melowetty.hsepermhelper.personalscheduleservice.model.SubGroupSelect
import com.melowetty.mapperlibrary.Mappable
import org.springframework.stereotype.Component

@Component
class ScheduleSettingsMapper: Mappable<ScheduleSettings, ScheduleSettingsDto> {
    override fun toDto(entity: ScheduleSettings): ScheduleSettingsDto {
        return ScheduleSettingsDto(
            baseSubGroup = entity.baseSubGroup,
            groupId = entity.groupId,
            subGroupSelect = entity.subGroupSelect.associate { Pair(it.subjectId, it.subGroup) },
            hiddenSubjects = entity.hiddenSubjects,
            includeQuarterSchedule = entity.includeQuarterSchedule,
            includeCommonEnglish = entity.includeCommonEnglish,
            includeCommonMinor = entity.includeCommonMinor,
        )
    }

    override fun toEntity(dto: ScheduleSettingsDto): ScheduleSettings {
        return ScheduleSettings(
            baseSubGroup = dto.baseSubGroup,
            groupId = dto.groupId,
            subGroupSelect = dto.subGroupSelect.map { SubGroupSelect(subjectId = it.key, subGroup = it.value) }.toSet(),
            hiddenSubjects = dto.hiddenSubjects,
            includeQuarterSchedule = dto.includeQuarterSchedule,
            includeCommonEnglish = dto.includeCommonEnglish,
            includeCommonMinor = dto.includeCommonMinor,
        )
    }
}