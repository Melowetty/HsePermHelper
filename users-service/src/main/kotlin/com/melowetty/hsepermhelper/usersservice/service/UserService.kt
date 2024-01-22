package com.melowetty.hsepermhelper.usersservice.service

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.model.UserCreatingRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface UserService {
    /**
     * Method returns user by he/she telegram ID
     * @param telegramId telegram ID of user
     * @return returns user object when it is found or null else
     */
    fun getUserByTelegramId(telegramId: Long): Mono<UserDto>

    /**
     * Method returns user by he/she ID
     * @param id ID of user
     * @return returns user object when it is found or null else
     */
    fun getUserById(id: UUID): Mono<UserDto>

    /**
     * Method creates user and return telegram ID when operation have success
     * @param userRequest User object
     */
    fun createUser(userRequest: UserCreatingRequest): Mono<Void>

    /**
     * Returns list of all users
     *
     * @return list of users
     */
    fun getUsers(): Flux<UserDto>

    /**
     * Delete user by id
     *
     * @param id user UUID
     * @return true if deleting is successful
     */
    fun deleteUserById(id: UUID): Mono<Boolean>

    /**
     * Delete user by telegram id
     *
     * @param telegramId user telegram id
     * @return true if deleting is successful
     */
    fun deleteUserByTelegramId(telegramId: Long): Mono<Boolean>

    /**
     * Full update user
     *
     * @param user new user data
     * @return new user object
     */
    fun updateUser(user: UserDto): Mono<UserDto>

    /**
     * Update user settings by patch method
     *
     * @param telegramId user telegram id
     * @param settings new user settings
     * @return new user object
     */
    fun updateUserSettingsByTelegramId(telegramId: Long, settings: Map<String, Any?>): Mono<UserDto>
}