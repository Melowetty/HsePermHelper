package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.Group
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, Long> {
    fun findByProgrammeAndDisplayName(programme: Programme, displayName: String): Group?
}