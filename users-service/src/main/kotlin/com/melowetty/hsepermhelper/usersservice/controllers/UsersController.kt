package com.melowetty.hsepermhelper.usersservice.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("users")
class UsersController {
    @GetMapping
    fun getAllUsers(): List<String> {
        return listOf("123", "1242", "124141")
    }
}