package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.languagessupportlibrary.model.Language

interface ProgrammeService {
    fun save(programme: Programme): Programme
    fun getAllProgramsByCourse(course: Int, lang: Language): List<ProgrammeDto>
}