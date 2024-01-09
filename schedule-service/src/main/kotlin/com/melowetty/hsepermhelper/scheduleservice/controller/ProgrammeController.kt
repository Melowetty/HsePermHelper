package com.melowetty.hsepermhelper.scheduleservice.controller

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.service.ProgrammeService
import com.melowetty.languagessupportlibrary.utils.LanguageUtils
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("programmes")
class ProgrammeController(
    private val programmeService: ProgrammeService,
) {
    @GetMapping
    fun getAllProgrammesByCourse(
        @RequestHeader headers: Map<String, String>,
        @RequestParam(required = true) course: Int,
    ): List<ProgrammeDto> {
        val lang = LanguageUtils.getLanguageFromHeaders(headers)
        return programmeService.getAllProgramsByCourse(course, lang)
    }
}