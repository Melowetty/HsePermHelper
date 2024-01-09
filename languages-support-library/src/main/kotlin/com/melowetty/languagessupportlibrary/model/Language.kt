package com.melowetty.languagessupportlibrary.model


enum class Language {
    RUSSIAN,
    ENGLISH;

    companion object {
        fun fromString(value: String): Language? {
            for (lang in Language.entries) {
                if (lang.name.equals(value, ignoreCase = true)) {
                    return lang
                }
            }
            return null
        }
    }
}