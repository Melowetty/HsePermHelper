package com.melowetty.hsepermhelper.usersservice.service.impl

import com.melowetty.hsepermhelper.usersservice.dto.SettingsDto
import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.mapper.UserMapper
import com.melowetty.hsepermhelper.usersservice.repository.UserRepository
import com.melowetty.hsepermhelper.usersservice.service.UserService
import org.springframework.stereotype.Service
import org.springframework.util.ReflectionUtils
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val mapper: UserMapper
): UserService {

    override fun getUsers(): List<UserDto> {
        return userRepository.findAll().toList().map {
            mapper.toDto(it)
        }
    }

    override fun getUsers(group: String, subGroup: Int): List<UserDto> {
        return userRepository.findAllBySettingsGroupAndSettingsSubGroup(group, subGroup).map { mapper.toDto(it) }
    }

    override fun getUserByTelegramId(telegramId: Long): UserDto? {
        val user = userRepository.findByTelegramId(telegramId) ?: return null
        return mapper.toDto(user)
    }

    override fun getUserById(id: UUID): UserDto? {
        val user = userRepository.findById(id).orElse(null) ?: return null
        return mapper.toDto(user)
    }

    override fun createUser(user: UserDto): Boolean {
        val isExists = userRepository.existsByTelegramId(user.telegramId)
        if(isExists) return false
        userRepository.save(mapper.toEntity(user))
//        val event = UsersChangedEvent(
//            user = user,
//            type = EventType.ADDED
//        )
//        eventPublisher.publishEvent(event)
        return true
    }

    override fun deleteUserById(id: UUID): Boolean {
        val user = userRepository.findById(id)
        if (user.isEmpty) return false
        userRepository.delete(user.get())
//        val event = UsersChangedEvent(
//            user = user.get().toDto(),
//            type = EventType.DELETED
//        )
//        eventPublisher.publishEvent(event)
        return true
    }

    override fun deleteUserByTelegramId(telegramId: Long): Boolean {
        val user = userRepository.findByTelegramId(telegramId) ?: return false
        userRepository.delete(user)
//        val event = UsersChangedEvent(
//            user = user.get().toDto(),
//            type = EventType.DELETED
//        )
//        eventPublisher.publishEvent(event)
        return true
    }

    override fun updateUser(user: UserDto): UserDto? {
        val userId = getUserByTelegramId(user.telegramId)?.id ?: return null
        val newUser = mapper.toDto(userRepository.save(
            mapper.toEntity(user.copy(id = userId))
        ))

//        val event = UsersChangedEvent(
//            user = newUser,
//            type = EventType.EDITED
//        )
//        eventPublisher.publishEvent(event)
        return newUser
    }

    override fun updateUserSettingsByTelegramId(telegramId: Long, settings: SettingsDto): UserDto? {
        val user = getUserByTelegramId(telegramId) ?: return null
        val newUser = mapper.toDto(userRepository.save(
            mapper.toEntity(user.copy(settings = settings))
        ))

//        val event = UsersChangedEvent(
//            user = newUser,
//            type = EventType.EDITED
//        )
//        eventPublisher.publishEvent(event)
        return newUser
    }

    override fun updateUserSettingsByTelegramId(telegramId: Long, settings: Map<String, Any?>): UserDto? {
        val user = getUserByTelegramId(telegramId) ?: return null
        val userSettings = user.settings.copy()
        val newSettings = settings.toMutableMap()
        newSettings.remove("id")
        newSettings.forEach { t, u ->
            val field = ReflectionUtils.findField(SettingsDto::class.java, t)
            if (field != null) {
                field.trySetAccessible()
                ReflectionUtils.setField(field, userSettings, u)
            }
        }
        return updateUserSettingsByTelegramId(telegramId, userSettings)
    }
}