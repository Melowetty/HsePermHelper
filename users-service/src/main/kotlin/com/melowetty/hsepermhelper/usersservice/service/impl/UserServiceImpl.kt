package com.melowetty.hsepermhelper.usersservice.service.impl

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.exception.UserAlreadyExistsException
import com.melowetty.hsepermhelper.usersservice.mapper.UserMapper
import com.melowetty.hsepermhelper.usersservice.model.Settings
import com.melowetty.hsepermhelper.usersservice.model.TelegramInfo
import com.melowetty.hsepermhelper.usersservice.model.User
import com.melowetty.hsepermhelper.usersservice.model.UserCreatingRequest
import com.melowetty.hsepermhelper.usersservice.repository.UserRepository
import com.melowetty.hsepermhelper.usersservice.service.UserService
import com.melowetty.languagessupportlibrary.model.Language
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val mapper: UserMapper
): UserService {

    override fun getUsers(): Flux<UserDto> {
        return userRepository.findAll().map {
            mapper.toDto(it)
        }
    }

    override fun getUserByTelegramId(telegramId: Long): Mono<UserDto> {
        return userRepository.findByTelegramInfo_TelegramId(telegramId).map { mapper.toDto(it) }
    }

    override fun getUserById(id: UUID): Mono<UserDto> {
        return userRepository.findById(id).map { mapper.toDto(it) }
    }

    override fun createUser(userRequest: UserCreatingRequest): Mono<Void> {
        return userRepository.existsByTelegramInfo_TelegramId(userRequest.telegramInfo.telegramId).flatMap { isExists ->
            if(isExists) return@flatMap Mono.error(UserAlreadyExistsException("Пользователь с таким Telegram ID уже существует!"))
            else {
                val settings = userRequest.settings.mapKeys { it.key.lowercase() }
                if (settings.containsKey("language").not()) {
                    return@flatMap Mono.error(IllegalArgumentException("Не указано обязательное поле с языком!"))
                }
                val languageValue = settings["language"].toString()
                val telegramInfo = userRequest.telegramInfo
                val user = User(
                    telegramInfo = TelegramInfo(
                        telegramId = telegramInfo.telegramId,
                        username = telegramInfo.username,
                        firstName = telegramInfo.firstName,
                        lastName = telegramInfo.lastName,
                    ),
                    settings = Settings(
                        language = Language.fromString(languageValue) ?: Language.RUSSIAN
                    )
                )
                userRepository.save(user)
                Mono.empty()
            }
        }
    }

    override fun deleteUserById(id: UUID): Mono<Boolean> {
        return userRepository.findById(id).flatMap { user ->
            userRepository.delete(user).then(Mono.fromCallable { true })
        }.defaultIfEmpty(false)
    }

    override fun deleteUserByTelegramId(telegramId: Long): Mono<Boolean> {
        return userRepository.findByTelegramInfo_TelegramId(telegramId).flatMap { user ->
            userRepository.delete(user).then(Mono.fromCallable { true })
        }.defaultIfEmpty(false)
    }

    override fun updateUser(user: UserDto): Mono<UserDto> {
        return Mono.empty()
//        return userRepository.findByTelegramInfo_TelegramId(user.telegramInfo.telegramId).flatMap { foundUser ->
//            val
//            userRepository.save(
//
//            ).map { mapper.toDto(it) }
//        }

//        val event = UsersChangedEvent(
//            user = newUser,
//            type = EventType.EDITED
//        )
//        eventPublisher.publishEvent(event)
    }

    override fun updateUserSettingsByTelegramId(telegramId: Long, settings: Map<String, Any?>): Mono<UserDto> {
        // todo добавить при изменении настроек пользователя запросы на другие сервисы
        return Mono.empty()
//        val user = getUserByTelegramId(telegramId) ?: return null
//        val userSettings = user.settings.copy()
//        val newSettings = settings.toMutableMap()
//        newSettings.remove("id")
//        newSettings.forEach { t, u ->
//            val field = ReflectionUtils.findField(SettingsDto::class.java, t)
//            if (field != null) {
//                field.trySetAccessible()
    //                ReflectionUtils.setField(field, userSettings, u)
//            }
//        }
//        return updateUserSettingsByTelegramId(telegramId, userSettings)
    }
}