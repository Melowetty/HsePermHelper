package com.melowetty.languagessupportlibrary.utils

import com.melowetty.languagessupportlibrary.model.Language

class LanguageUtils {
    companion object {
        private const val LANGUAGE_HEADER = "X-Language"

        /**
         * Get language from headers in http request
         *
         * @param headers headers from http request as key value
         * @return language from headers or return russian when language is not contains in headers
         */
        fun getLanguageFromHeaders(headers: Map<String, String>): Language {
            val mappedHeaders = headers.mapKeys { it.key.lowercase() }
            val headerValue = mappedHeaders.get(LANGUAGE_HEADER.lowercase())
            return if(headerValue != null) {
                Language.fromString(headerValue) ?: Language.RUSSIAN
            } else Language.RUSSIAN
        }

        /**
         * Add language to headers in http request
         *
         * @param headers other headers in http request
         * @param language language, which must added to headers
         * @return new headers
         */
        fun addLanguageToHeaders(headers: Map<String, String>, language: Language): Map<String, String> {
            val copiedHeaders = headers.toMutableMap()
            copiedHeaders[LANGUAGE_HEADER] = language.name
            return copiedHeaders
        }
    }
}