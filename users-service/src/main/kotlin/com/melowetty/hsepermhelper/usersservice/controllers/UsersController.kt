package com.melowetty.hsepermhelper.usersservice.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UsersController {
    @GetMapping("all")
    fun getAllUsers(): List<String> {
        return listOf("123", "1242", "124141")
    }

    @GetMapping
    fun getAllAges(): List<Int> {
        return listOf(1, 2, 3)
    }
}