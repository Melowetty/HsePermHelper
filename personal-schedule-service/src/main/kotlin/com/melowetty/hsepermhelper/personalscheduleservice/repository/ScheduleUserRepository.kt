package com.melowetty.hsepermhelper.personalscheduleservice.repository

import com.melowetty.hsepermhelper.personalscheduleservice.model.ScheduleUser
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ScheduleUserRepository : CrudRepository<ScheduleUser, UUID> {
}