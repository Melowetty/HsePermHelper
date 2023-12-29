package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.model.Language

class MapperWithLanguage {
    companion object {
        fun <T: Translatable> getMapWithKeyByLanguage(mappers: List<T>): Map<Language, T> {
            val mapper = mutableMapOf<Language, T>()
            mappers.forEach {
                mapper[it.getLanguage()] = it
            }
            return mapper
        }
    }
}