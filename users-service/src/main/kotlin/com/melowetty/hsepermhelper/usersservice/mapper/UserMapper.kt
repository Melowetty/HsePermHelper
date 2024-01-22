package com.melowetty.hsepermhelper.usersservice.mapper

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.model.User
import com.melowetty.mapperlibrary.MappableToDto
import org.springframework.stereotype.Component

@Component
class UserMapper(
    private val telegramInfoMapper: TelegramInfoMapper,
): MappableToDto<User, UserDto> {
    override fun toDto(entity: User): UserDto {
        return UserDto(
            id = entity.id,
            settings = mapOf("language" to entity.settings.language),
            creationDate = entity.creationDate,
            telegramInfo = telegramInfoMapper.toDto(entity.telegramInfo)
        )
    }
}