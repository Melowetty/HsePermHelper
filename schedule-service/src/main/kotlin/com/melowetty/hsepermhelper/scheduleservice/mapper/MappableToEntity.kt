package com.melowetty.hsepermhelper.scheduleservice.mapper

interface MappableToEntity<DTO, ENTITY> {
    fun toEntity(dto: DTO): ENTITY
}