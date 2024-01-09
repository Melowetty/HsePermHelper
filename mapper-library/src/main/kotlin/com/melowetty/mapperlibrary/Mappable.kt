package com.melowetty.mapperlibrary

interface Mappable<ENTITY, DTO> : MappableToEntity<DTO, ENTITY>, MappableToDto<ENTITY, DTO> {
}