package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.model.Language

interface Translatable {
    fun getLanguage(): Language
}