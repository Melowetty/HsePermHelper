package com.melowetty.hsepermhelper.scheduleservice.mapper

interface MappableToDto<ENTITY, DTO> {
    fun toDto(entity: ENTITY): DTO
}