package com.melowetty.hsepermhelper.schedulefilesservice.service

import com.melowetty.hsepermhelper.schedulefilesservice.model.ScheduleFile

interface ScheduleFileService {
    fun getScheduleFiles(): List<ScheduleFile>
}