package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.Group
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.hsepermhelper.scheduleservice.model.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository: JpaRepository<Subject, Long> {
    fun findByProgrammeAndName(programme: Programme, name: String): Subject?
}