package com.melowetty.mapperlibrary

interface MappableToEntity<DTO, ENTITY> {
    fun toEntity(dto: DTO): ENTITY
}