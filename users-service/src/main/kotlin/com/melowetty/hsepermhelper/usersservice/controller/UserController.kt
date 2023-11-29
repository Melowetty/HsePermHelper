package com.melowetty.hsepermhelper.usersservice.controller

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService
) {
    @GetMapping("users")
    fun getUsers(): List<UserDto> {
        return userService.getUsers()
    }

    @Operation(
        summary = "Регистрация пользователя",
        description = "Позволяет зарегистрировать пользователя"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        "users",
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(
        @RequestBody userDto: UserDto,
    ): ResponseEntity<Any> {
        val result = userService.createUser(userDto)
        if(userDto.settings.group.isBlank() || userDto.settings.subGroup < 0) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapOf(Pair("message", "Неверно указана группа или подгруппа!")))
        }
        val status = if (result) HttpStatus.CREATED else HttpStatus.CONFLICT
        return ResponseEntity.status(status).build()
    }
}