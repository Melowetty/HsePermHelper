package com.melowetty.hsepermhelper.schedulefilesservice.controller

import com.melowetty.hsepermhelper.schedulefilesservice.model.ScheduleFile
import com.melowetty.hsepermhelper.schedulefilesservice.service.ScheduleFileService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("schedule_files")
class ScheduleFilesController(
    private val scheduleFileService: ScheduleFileService
) {
    @GetMapping
    fun getScheduleFiles(): List<ScheduleFile> {
        return scheduleFileService.getScheduleFiles()
    }
}