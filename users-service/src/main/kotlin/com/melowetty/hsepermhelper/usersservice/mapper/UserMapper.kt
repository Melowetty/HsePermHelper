package com.melowetty.hsepermhelper.usersservice.mapper

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.model.TelegramInfo
import com.melowetty.hsepermhelper.usersservice.model.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserMapper(
    private val settingsMapper: SettingsMapper,
    private val telegramInfoMapper: TelegramInfoMapper,
) {

    fun toEntity(userDto: UserDto): User {
        return User(
            id = userDto.id,
            telegramId = userDto.telegramId,
            settings = settingsMapper.toEntity(userDto.settings),
            creationDate = userDto.creationDate,
            telegramInfo = userDto.telegramInfo?.let { telegramInfoMapper.toEntity(it) }
        )
    }

    fun toDto(user: User): UserDto {
        return UserDto(
            id = user.id,
            telegramId = user.telegramId,
            settings = settingsMapper.toDto(user.settings),
            creationDate = user.creationDate ?: LocalDateTime.now(),
            telegramInfo = user.telegramInfo?.let { telegramInfoMapper.toDto(it) }
        )
    }
}