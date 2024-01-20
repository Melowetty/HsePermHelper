package com.melowetty.hsepermhelper.usersservice.repository

import com.melowetty.hsepermhelper.usersservice.model.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface UserRepository: ReactiveMongoRepository<User, UUID> {
    fun findByTelegramInfo_TelegramId(telegramId: Long): Mono<User>
    fun existsByTelegramInfo_TelegramId(telegramId: Long): Mono<Boolean>
}