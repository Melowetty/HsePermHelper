package com.melowetty.hsepermhelper.usersservice.service

import com.melowetty.hsepermhelper.usersservice.dto.SettingsDto
import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import java.util.*

interface UserService {
    /**
     * Method returns user by he/she telegram ID
     * @param telegramId telegram ID of user
     * @return returns user object when it is found or null else
     */
    fun getUserByTelegramId(telegramId: Long): UserDto?

    /**
     * Method returns user by he/she ID
     * @param id ID of user
     * @return returns user object when it is found or null else
     */
    fun getUserById(id: UUID): UserDto?

    /**
     * Method creates user and return telegram ID when operation have success
     * @param user User object
     * @return true if user is created
     */
    fun createUser(user: UserDto): Boolean

    /**
     * Returns list of all users
     *
     * @return list of users
     */
    fun getUsers(): List<UserDto>

    /**
     * Delete user by id
     *
     * @param id user UUID
     * @return true if deleting is successful
     */
    fun deleteUserById(id: UUID): Boolean

    /**
     * Delete user by telegram id
     *
     * @param telegramId user telegram id
     * @return true if deleting is successful
     */
    fun deleteUserByTelegramId(telegramId: Long): Boolean

    /**
     * Full update user
     *
     * @param user new user data
     * @return new user object
     */
    fun updateUser(user: UserDto): UserDto?

    /**
     * Update user settings
     *
     * @param telegramId user telegram id
     * @param settings new user settings
     * @return new user object
     */
    fun updateUserSettingsByTelegramId(telegramId: Long, settings: SettingsDto): UserDto?

    /**
     * Update user settings by patch method
     *
     * @param telegramId user telegram id
     * @param settings new user settings
     * @return new user object
     */
    fun updateUserSettingsByTelegramId(telegramId: Long, settings: Map<String, Any?>): UserDto?
}