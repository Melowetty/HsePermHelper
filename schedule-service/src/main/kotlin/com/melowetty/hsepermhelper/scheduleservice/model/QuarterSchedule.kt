package com.melowetty.hsepermhelper.scheduleservice.model

import jakarta.persistence.Entity
import java.time.LocalDate
import java.util.*

@Entity
class QuarterSchedule(
    id: Long? = null,
    systemId: UUID,
    val quarterNum: Int,
    lessons: List<BaseLesson>,
    quarterStart: LocalDate,
    quarterEnd: LocalDate
): BaseSchedule(
    id = id,
    systemId = systemId,
    lessons = lessons,
    scheduleStart = quarterStart,
    scheduleEnd = quarterEnd,
    scheduleType = ScheduleType.QUARTER_SCHEDULE,
)