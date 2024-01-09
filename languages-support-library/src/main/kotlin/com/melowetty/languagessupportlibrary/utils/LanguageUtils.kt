package com.melowetty.languagessupportlibrary.utils

import com.melowetty.languagessupportlibrary.model.Language

class LanguageUtils {
    companion object {
        private const val LANGUAGE_HEADER = "X-Language"
        fun getLanguageFromHeaders(headers: Map<String, String>): Language {
            val headerValue = headers.get(LANGUAGE_HEADER)
            return if(headerValue != null) {
                Language.fromString(headerValue) ?: Language.RUSSIAN
            } else Language.RUSSIAN
        }
    }
}