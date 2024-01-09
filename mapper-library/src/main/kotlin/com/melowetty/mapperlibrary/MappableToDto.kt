package com.melowetty.mapperlibrary

interface MappableToDto<ENTITY, DTO> {
    fun toDto(entity: ENTITY): DTO
}