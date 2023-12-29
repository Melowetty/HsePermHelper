package com.melowetty.hsepermhelper.scheduleservice.utils

import com.ibm.icu.text.Transliterator

class TranslateUtils {
    companion object {
        private const val CYRILLIC_TO_LATIN = "Russian-Latin/BGN"
        fun translate(russian: String): String {
            return Transliterator.getInstance(CYRILLIC_TO_LATIN).transliterate(russian)
        }
    }
}