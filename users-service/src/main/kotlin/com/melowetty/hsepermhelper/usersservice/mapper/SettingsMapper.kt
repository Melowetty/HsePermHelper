package com.melowetty.hsepermhelper.usersservice.mapper

import com.melowetty.hsepermhelper.usersservice.dto.SettingsDto
import com.melowetty.hsepermhelper.usersservice.model.Settings
import org.springframework.stereotype.Component

@Component
class SettingsMapper {
    fun toEntity(settingsDto: SettingsDto): Settings {
        return Settings(
            id = settingsDto.id,
            language = settingsDto.language,
            isEnabledRemoteCalendar = settingsDto.isEnabledRemoteCalendar,
            isEnabledNewCommonScheduleNotifications = settingsDto.isEnabledNewCommonScheduleNotifications,
            isEnabledNewQuarterScheduleNotifications = settingsDto.isEnabledNewQuarterScheduleNotifications,
        )
    }

    fun toDto(settings: Settings): SettingsDto {
        return SettingsDto(
            id = settings.id,
            language = settings.language,
            isEnabledRemoteCalendar = settings.isEnabledRemoteCalendar,
            isEnabledNewCommonScheduleNotifications = settings.isEnabledNewCommonScheduleNotifications,
            isEnabledNewQuarterScheduleNotifications = settings.isEnabledNewQuarterScheduleNotifications,
        )
    }
}