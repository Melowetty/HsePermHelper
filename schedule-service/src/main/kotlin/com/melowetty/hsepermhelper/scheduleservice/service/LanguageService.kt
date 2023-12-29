package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.model.Language
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.stereotype.Component

@Component
class LanguageService(
    private val env: Environment
) {
    private val languageHeader = env["app.language-header"] ?: "X-Language"

    fun getLanguageFromHeaders(request: HttpServletRequest): Language {
        val headers = request.headerNames.toList().map { it.lowercase() }
        val langContains = headers.contains(languageHeader.lowercase())
        return if(langContains) {
            val langValue = request.getHeader(languageHeader)
            Language.fromString(langValue) ?: Language.RUSSIAN
        } else Language.RUSSIAN
    }
}