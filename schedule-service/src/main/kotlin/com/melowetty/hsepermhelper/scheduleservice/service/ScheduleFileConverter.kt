package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import java.io.InputStream

interface ScheduleFileConverter {
    fun convertInputStreamToScheduleDto(inputStream: InputStream): ScheduleDto
}