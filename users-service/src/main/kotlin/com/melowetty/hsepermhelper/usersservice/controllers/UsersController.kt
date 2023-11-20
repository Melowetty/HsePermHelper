package com.melowetty.hsepermhelper.usersservice.controllers

import com.melowetty.hsepermhelper.shared.model.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class UsersController {
    @GetMapping("all")
    fun getAllUsers(): Response<List<String>> {
        return Response(listOf("123", "1242", "124141"))
    }

    @GetMapping
    fun getAllAges(): Response<List<Int>> {
        return Response(listOf(1, 2, 3))
    }
}