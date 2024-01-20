package com.melowetty.hsepermhelper.usersservice.mapper

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val settingsMapper: SettingsMapper,
    private val telegramInfoMapper: TelegramInfoMapper,
) {

    fun toEntity(userDto: UserDto): User {
        return User(
            id = userDto.id,
            settings = settingsMapper.toEntity(userDto.settings),
            creationDate = userDto.creationDate,
            telegramInfo = userDto.telegramInfo?.let { telegramInfoMapper.toEntity(it) }
        )
    }

    fun toDto(user: User): UserDto {
        return UserDto(
            id = user.id,
            settings = settingsMapper.toDto(user.settings),
            creationDate = user.creationDate,
            telegramInfo = user.telegramInfo?.let { telegramInfoMapper.toDto(it) }
        )
    }
}