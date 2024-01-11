package com.melowetty.hsepermhelper.personalscheduleservice.mapper

import com.melowetty.hsepermhelper.personalscheduleservice.dto.ScheduleUserDto
import com.melowetty.hsepermhelper.personalscheduleservice.model.ScheduleUser
import com.melowetty.mapperlibrary.Mappable
import org.springframework.stereotype.Component

@Component
class ScheduleUserMapper(
    private val scheduleSettingsMapper: ScheduleSettingsMapper
): Mappable<ScheduleUser, ScheduleUserDto> {
    override fun toDto(entity: ScheduleUser): ScheduleUserDto {
        return ScheduleUserDto(
            id = entity.uuid,
            settings = scheduleSettingsMapper.toDto(entity.settings)
        )
    }

    override fun toEntity(dto: ScheduleUserDto): ScheduleUser {
        return ScheduleUser(
            uuid = dto.id,
            settings = scheduleSettingsMapper.toEntity(dto.settings)
        )
    }
}