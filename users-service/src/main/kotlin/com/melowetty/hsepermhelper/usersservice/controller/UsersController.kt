package com.melowetty.hsepermhelper.usersservice.controller

import com.melowetty.hsepermhelper.usersservice.dto.UserDto
import com.melowetty.hsepermhelper.usersservice.exception.UserNotFoundException
import com.melowetty.hsepermhelper.usersservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("users")
class UsersController(
    private val userService: UserService
) {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    // TODO: Необходимо добавить пагинацию
    fun getUsers(): Flux<UserDto> {
        return userService.getUsers()
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(
        @RequestBody userDto: UserDto,
    ): Mono<ResponseEntity<Void>> {
        return userService.createUser(userDto).map { result ->
            val status = if (result) HttpStatus.CREATED else HttpStatus.CONFLICT
            ResponseEntity.status(status).build()
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
        "users",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateUser(
        @RequestBody userDto: UserDto,
    ): Mono<UserDto> {
        return userService.updateUser(userDto).switchIfEmpty(throw UserNotFoundException())
    }
}