package com.melowetty.hsepermhelper.scheduleservice.utils

import com.melowetty.hsepermhelper.scheduleservice.model.Language
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value

class LanguageUtils {
    companion object {
        @Value(value = "\${app.language-header}")
        lateinit var languageHeader: String
        fun getLanguageFromHeaders(request: HttpServletRequest): Language {
            val headers = request.headerNames.toList().map { it.lowercase() }
            val langContains = headers.contains(languageHeader)
            return if(langContains) {
                val langValue = request.getHeader(languageHeader)
                Language.fromString(langValue) ?: Language.RUSSIAN
            } else Language.RUSSIAN
        }
    }
}