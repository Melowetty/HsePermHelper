package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.model.Programme

interface ProgrammeService {
    fun save(programme: Programme): Programme
    fun getAllProgramsByCourse(course: Int, lang: Language): List<ProgrammeDto>
}