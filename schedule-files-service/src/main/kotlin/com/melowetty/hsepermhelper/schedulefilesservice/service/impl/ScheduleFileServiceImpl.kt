package com.melowetty.hsepermhelper.schedulefilesservice.service.impl

import com.melowetty.hsepermhelper.schedulefilesservice.model.ScheduleFile
import com.melowetty.hsepermhelper.schedulefilesservice.repository.ScheduleFileRepository
import com.melowetty.hsepermhelper.schedulefilesservice.service.ScheduleFileService
import org.springframework.stereotype.Service

@Service
class ScheduleFileServiceImpl(
    private val scheduleFileRepository: ScheduleFileRepository
): ScheduleFileService {
    override fun getScheduleFiles(): List<ScheduleFile> {
        return scheduleFileRepository.getScheduleFiles()
    }
}