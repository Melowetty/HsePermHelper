package com.melowetty.hsepermhelper.usersservice.repository

import com.melowetty.hsepermhelper.usersservice.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, UUID> {
    fun findByTelegramId(telegramId: Long): User?
    fun existsByTelegramId(telegramId: Long): Boolean
}