package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import org.springframework.data.jpa.repository.JpaRepository

interface ProgrammeRepository: JpaRepository<Programme, Long> {
    fun findByNameAndCourse(name: String, course: Int): Programme?
}