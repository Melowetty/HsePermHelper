package com.melowetty.hsepermhelper.usersservice.service.impl

import com.melowetty.hsepermhelper.usersservice.dto.SettingsDto
import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.mapper.UserMapper
import com.melowetty.hsepermhelper.usersservice.repository.UserRepository
import com.melowetty.hsepermhelper.usersservice.service.UserService
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

    override fun createUser(user: UserDto): Mono<Boolean> {
        if(user.telegramInfo == null) return Mono.just(false)
        return userRepository.existsByTelegramInfo_TelegramId(user.telegramInfo.telegramId).flatMap { isExists ->
            if(isExists) Mono.just(false)
            else {
                userRepository.save(mapper.toEntity(user)).map {
                    it != null
                }
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
        if(user.telegramInfo == null) return Mono.empty()
        return userRepository.findByTelegramInfo_TelegramId(user.telegramInfo.telegramId).flatMap { foundUser ->
            userRepository.save(
                mapper.toEntity(user.copy(id = foundUser.id))
            ).map { mapper.toDto(it) }
        }

//        val event = UsersChangedEvent(
//            user = newUser,
//            type = EventType.EDITED
//        )
//        eventPublisher.publishEvent(event)
    }

    override fun updateUserSettingsByTelegramId(telegramId: Long, settings: SettingsDto): Mono<UserDto> {
        // todo добавить при изменении настроек пользователя запросы на другие сервисы
        return Mono.empty()
//        val user = getUserByTelegramId(telegramId) ?: return null
//        val newUser = userRepository.save(
//            mapper.toEntity(user.copy(settings = settings))
//        ).map { mapper.toDto(it) }
//
////        val event = UsersChangedEvent(
////            user = newUser,
////            type = EventType.EDITED
////        )
////        eventPublisher.publishEvent(event)
//        return newUser
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