package com.melowetty.hsepermhelper.personalscheduleservice.model

import java.time.LocalDate
import java.util.*

data class CommonSchedule(
    val uuid: UUID,
    val weekNumber: Int?,
    val lessons: List<CommonScheduleLesson>,
    val weekStart: LocalDate,
    val weekEnd: LocalDate,
    val scheduleType: ScheduleType
)