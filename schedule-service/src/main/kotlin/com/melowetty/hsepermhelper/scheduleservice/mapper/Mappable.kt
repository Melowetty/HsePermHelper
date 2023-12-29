package com.melowetty.hsepermhelper.scheduleservice.mapper

interface Mappable<A, B> {
    fun toEntity(dto: B): A
    fun toDto(entity: A): B
}