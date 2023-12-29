package com.melowetty.hsepermhelper.scheduleservice.controller

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.service.LanguageService
import com.melowetty.hsepermhelper.scheduleservice.service.ProgrammeService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("programmes")
class ProgrammeController(
    private val programmeService: ProgrammeService,
    private val languageService: LanguageService,
) {
    @GetMapping
    fun getAllProgrammesByCourse(
        request: HttpServletRequest,
        @RequestParam(required = true) course: Int,
    ): List<ProgrammeDto> {
        val lang = languageService.getLanguageFromHeaders(request)
        return programmeService.getAllProgramsByCourse(course, lang)
    }
}