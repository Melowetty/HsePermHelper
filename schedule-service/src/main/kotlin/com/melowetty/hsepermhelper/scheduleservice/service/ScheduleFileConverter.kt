package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import java.io.InputStream
import java.util.UUID

interface ScheduleFileConverter {
    fun convertInputStreamToScheduleDto(uuid: UUID, inputStream: InputStream): BaseScheduleDto?
}