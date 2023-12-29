package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.model.Language

open class MapperWithLanguage<T: Translatable> (
    instances: List<T>,
    private val defaultInstance: T
) {
    private val instanceMapper: Map<Language, T> = getMapWithKeyByLanguage(instances)
    private fun getMapWithKeyByLanguage(mappers: List<T>): Map<Language, T> {
        val mapper = mutableMapOf<Language, T>()
        mappers.forEach {
            mapper[it.getLanguage()] = it
        }
        return mapper
    }
    fun getMapper(language: Language): T {
        return instanceMapper.getOrDefault(language, defaultInstance)
    }

}