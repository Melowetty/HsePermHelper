package com.melowetty.hsepermhelper.usersservice.mapper

import com.melowetty.hsepermhelper.usersservice.dto.TelegramInfoDto
import com.melowetty.hsepermhelper.usersservice.model.TelegramInfo
import org.springframework.stereotype.Component

@Component
class TelegramInfoMapper {
    fun toEntity(telegramInfoDto: TelegramInfoDto): TelegramInfo {
        return TelegramInfo(
            firstName = telegramInfoDto.firstName,
            lastName = telegramInfoDto.lastName,
            username = telegramInfoDto.username,
        )
    }

    fun toDto(telegramInfo: TelegramInfo): TelegramInfoDto {
        return TelegramInfoDto(
            firstName = telegramInfo.firstName,
            lastName = telegramInfo.lastName,
            username = telegramInfo.username,
        )
    }
}