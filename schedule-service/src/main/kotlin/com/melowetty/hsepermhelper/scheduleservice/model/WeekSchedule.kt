package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.Entity
import java.time.LocalDate
import java.util.*

@Entity
class WeekSchedule(
    id: Long? = null,
    systemId: UUID,
    val weekNumber: Int?,
    lessons: List<BaseLesson>,
    weekStart: LocalDate,
    weekEnd: LocalDate,
    scheduleType: ScheduleType,
): BaseSchedule(
    id = id,
    systemId = systemId,
    lessons = lessons,
    scheduleStart = weekStart,
    scheduleEnd = weekEnd,
    scheduleType = scheduleType,
)