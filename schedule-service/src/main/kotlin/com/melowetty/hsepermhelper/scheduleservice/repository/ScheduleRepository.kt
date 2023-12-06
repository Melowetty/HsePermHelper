package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.Group
import com.melowetty.hsepermhelper.scheduleservice.model.Schedule
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ScheduleRepository: CrudRepository<Schedule, UUID> {
}