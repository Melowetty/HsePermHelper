package com.melowetty.hsepermhelper.usersservice.controller

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.exception.UserNotFoundException
import com.melowetty.hsepermhelper.usersservice.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("user")
@RestController
@Tag(name = "Пользователь", description = "Взаимодействие с пользователем")
class UserController(
    private val userService: UserService
) {
    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Получение пользователя по Telegram ID",
        description = "Позволяет получить пользователя по его Telegram ID"
    )
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserByTelegramId(
        @Parameter(description = "Telegram ID пользователя")
        @RequestParam("telegramId")
        telegramId: Long,
    ): UserDto {
        return userService.getUserByTelegramId(telegramId = telegramId) ?: throw UserNotFoundException()
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Получение пользователя",
        description = "Позволяет получить пользователя по его ID"
    )
    @GetMapping(
        "{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserById(
        @Parameter(description = "ID пользователя")
        @PathVariable("id")
        id: UUID,
    ): UserDto {
        return userService.getUserById(id) ?: throw UserNotFoundException()
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Удаление пользователя",
        description = "Позволяет удалить пользователя по его ID"
    )
    @DeleteMapping(
        "{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteUserById(
        @Parameter(description = "ID пользователя")
        @PathVariable("id")
        id: UUID,
    ) : ResponseEntity<Unit> {
        val result = userService.deleteUserById(id)
        val status = if (result) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(status).build()
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Удаление пользователя",
        description = "Позволяет удалить пользователя по его Telegram ID"
    )
    @DeleteMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteUserByTelegramId(
        @Parameter(description = "Telegram ID пользователя")
        @RequestParam("telegramId")
        telegramId: Long,
    ): ResponseEntity<Unit> {
        val result = userService.deleteUserByTelegramId(telegramId)
        val status = if (result) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR
        return ResponseEntity.status(status).build()
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Изменение настроек пользователя",
        description = "Позволяет изменить настройки пользователя по Telegram ID"
    )
    @PatchMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateUserByTelegramId(
        @Parameter(description = "Telegram ID пользователя")
        @RequestParam("telegramId")
        telegramId: Long,
        @RequestBody
        @Parameter(description = "Новые настройки пользователя")
        settings: Map<String, Any>,
    ): UserDto {
        return userService.updateUserSettingsByTelegramId(telegramId, settings) ?: throw UserNotFoundException()
    }
}