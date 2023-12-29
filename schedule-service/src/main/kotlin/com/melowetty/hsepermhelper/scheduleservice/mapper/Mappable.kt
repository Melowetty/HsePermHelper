package com.melowetty.hsepermhelper.scheduleservice.mapper

interface Mappable<ENTITY, DTO> : MappableToEntity<DTO, ENTITY>, MappableToDto<ENTITY, DTO> {
}