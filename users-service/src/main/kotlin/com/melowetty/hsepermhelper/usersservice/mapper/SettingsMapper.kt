package com.melowetty.hsepermhelper.usersservice.mapper

import com.melowetty.hsepermhelper.usersservice.dto.SettingsDto
import com.melowetty.hsepermhelper.usersservice.model.Settings
import org.springframework.stereotype.Component

@Component
class SettingsMapper {
    fun toEntity(settingsDto: SettingsDto): Settings {
        return Settings(
            language = settingsDto.language,
        )
    }

    fun toDto(settings: Settings): SettingsDto {
        return SettingsDto(
            language = settings.language,
        )
    }
}