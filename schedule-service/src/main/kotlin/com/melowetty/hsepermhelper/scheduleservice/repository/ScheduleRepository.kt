package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ScheduleRepository: JpaRepository<Schedule, UUID> {
    @Query("SELECT schedule FROM Schedule schedule LEFT JOIN FETCH schedule.lessons lesson WHERE lesson.group.id = :groupId")
    fun getSchedulesByGroupId(groupId: Long): List<Schedule>
    @Query("SELECT schedule FROM Schedule schedule LEFT JOIN FETCH schedule.lessons lesson WHERE lesson.group.programme.id = :programmeId")
    fun getSchedulesByProgrammeId(programmeId: Long): List<Schedule>
}