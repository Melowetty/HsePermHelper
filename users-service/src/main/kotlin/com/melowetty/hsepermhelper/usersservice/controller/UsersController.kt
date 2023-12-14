package com.melowetty.hsepermhelper.usersservice.controller

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.exception.UserNotFoundException
import com.melowetty.hsepermhelper.usersservice.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
@RestController
@RequestMapping("users")
class UsersController(
    private val userService: UserService
) {
    @Operation(
        summary = "Список пользователей",
        description = "Позволяет получить список всех пользователей"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    // TODO: Необходимо добавить пагинацию
    fun getUsers(): List<UserDto> {
        return userService.getUsers()
    }

    @Operation(
        summary = "Регистрация пользователя",
        description = "Позволяет зарегистрировать пользователя"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(
        @RequestBody userDto: UserDto,
    ): ResponseEntity<Any> {
        val result = userService.createUser(userDto)
        val status = if (result) HttpStatus.CREATED else HttpStatus.CONFLICT
        return ResponseEntity.status(status).build()
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
        summary = "Обновление пользователя",
        description = "Позволяет обновить данные пользователя"
    )
    @PutMapping(
        "users",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateUser(
        @RequestBody userDto: UserDto,
    ): UserDto {
        return userService.updateUser(userDto) ?: throw UserNotFoundException()
    }
}