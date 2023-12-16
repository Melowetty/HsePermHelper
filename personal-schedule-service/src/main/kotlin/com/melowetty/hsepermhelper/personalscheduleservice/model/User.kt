package com.melowetty.hsepermhelper.personalscheduleservice.model

import java.util.UUID

data class User(
    val uuid: UUID,
    val settings: Settings,
)
