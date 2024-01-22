package com.melowetty.hsepermhelper.usersservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT)
class UserAlreadyExistsException(msg: String): IllegalArgumentException(msg)