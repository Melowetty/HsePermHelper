package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.BaseSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ScheduleRepository: JpaRepository<BaseSchedule, UUID> {
    @Query("SELECT schedule FROM BaseSchedule schedule LEFT JOIN FETCH schedule.lessons lesson WHERE lesson.group.id = :groupId")
    fun getSchedulesByGroupId(groupId: Long): List<BaseSchedule>
    @Query("SELECT schedule FROM BaseSchedule schedule LEFT JOIN FETCH schedule.lessons lesson WHERE lesson.group.programme.id = :programmeId")
    fun getSchedulesByProgrammeId(programmeId: Long): List<BaseSchedule>
}