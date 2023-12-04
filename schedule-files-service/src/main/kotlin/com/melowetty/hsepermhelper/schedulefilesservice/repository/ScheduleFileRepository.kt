package com.melowetty.hsepermhelper.schedulefilesservice.repository

import com.melowetty.hsepermhelper.schedulefilesservice.model.ScheduleFile

interface ScheduleFileRepository {
    fun getScheduleFiles(): List<ScheduleFile>
}