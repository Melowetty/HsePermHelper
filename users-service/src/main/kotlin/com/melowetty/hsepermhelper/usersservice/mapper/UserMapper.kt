package com.melowetty.hsepermhelper.usersservice.mapper

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.model.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserMapper(
    private val settingsMapper: SettingsMapper
) {

    fun toEntity(userDto: UserDto): User {
        return User(
            id = userDto.id,
            telegramId = userDto.telegramId,
            settings = settingsMapper.toEntity(userDto.settings),
            creationDate = userDto.creationDate
        )
    }

    fun toDto(user: User): UserDto {
        return UserDto(
            id = user.id,
            telegramId = user.telegramId,
            settings = settingsMapper.toDto(user.settings),
            creationDate = user.creationDate ?: LocalDateTime.now(),
        )
    }
}