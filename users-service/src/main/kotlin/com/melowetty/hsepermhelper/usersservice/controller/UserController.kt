package com.melowetty.hsepermhelper.usersservice.controller

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.exception.UserNotFoundException
import com.melowetty.hsepermhelper.usersservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RequestMapping("user")
@RestController
class UserController(
    private val userService: UserService
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserByTelegramId(
        @RequestParam("telegramId")
        telegramId: Long,
    ): Mono<UserDto> {
        return userService.getUserByTelegramId(telegramId = telegramId).switchIfEmpty(throw UserNotFoundException())
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        "{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getUserById(
        @PathVariable("id")
        id: UUID,
    ): Mono<UserDto> {
        return userService.getUserById(id).switchIfEmpty(throw UserNotFoundException())
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
        "{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteUserById(
        @PathVariable("id")
        id: UUID,
    ): Mono<ResponseEntity<Void>> {
        return userService.deleteUserById(id).map { result ->
            val status = if (result) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR
            ResponseEntity.status(status).build()
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun deleteUserByTelegramId(
        @RequestParam("telegramId")
        telegramId: Long,
    ): Mono<ResponseEntity<Void>> {
        return userService.deleteUserByTelegramId(telegramId).map { result ->
            val status = if (result) HttpStatus.OK else HttpStatus.INTERNAL_SERVER_ERROR
            ResponseEntity.status(status).build()
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateUserByTelegramId(
        @RequestParam("telegramId")
        telegramId: Long,
        @RequestBody
        settings: Map<String, Any>,
    ): Mono<UserDto> {
        return userService.updateUserSettingsByTelegramId(telegramId, settings).switchIfEmpty(throw UserNotFoundException())
    }
}