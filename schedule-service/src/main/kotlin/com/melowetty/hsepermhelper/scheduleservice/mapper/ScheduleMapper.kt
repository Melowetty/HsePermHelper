package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.dto.QuarterScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.dto.WeekScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.BaseSchedule
import com.melowetty.hsepermhelper.scheduleservice.model.QuarterSchedule
import com.melowetty.hsepermhelper.scheduleservice.model.WeekSchedule

class ScheduleMapper(
    private val lessonMapper: LessonMapper
) {
    fun toEntity(schedule: BaseScheduleDto): BaseSchedule {
        when (schedule) {
            is WeekScheduleDto -> {
                return WeekSchedule(
                    systemId = schedule.uuid,
                    weekNumber = schedule.weekNumber,
                    lessons = schedule.lessons.map { lessonMapper.toEntity(it) },
                    weekStart = schedule.scheduleStart,
                    weekEnd = schedule.scheduleEnd,
                    scheduleType = schedule.scheduleType,
                )
            }
            is QuarterScheduleDto -> {
                return QuarterSchedule(
                    systemId = schedule.uuid,
                    quarterNum = schedule.quarterNumber,
                    lessons = schedule.lessons.map { lessonMapper.toEntity(it) },
                    quarterStart = schedule.scheduleStart,
                    quarterEnd = schedule.scheduleEnd,
                )
            }

            else -> throw IllegalArgumentException("Неверный тип расписания")
        }
    }

    fun toDto(schedule: BaseSchedule): BaseScheduleDto {
        when (schedule) {
            is WeekSchedule -> {
                return WeekScheduleDto(
                    uuid = schedule.systemId,
                    weekNumber = schedule.weekNumber,
                    lessons = schedule.lessons.map { lessonMapper.toDto(schedule.scheduleType, it) },
                    weekStart = schedule.scheduleStart,
                    weekEnd = schedule.scheduleEnd,
                    scheduleType = schedule.scheduleType
                )
            }
            is QuarterSchedule -> {
                return QuarterScheduleDto(
                    uuid = schedule.systemId,
                    quarterNumber = schedule.quarterNum,
                    lessons = schedule.lessons.map { lessonMapper.toDto(schedule.scheduleType, it) },
                    quarterStart = schedule.scheduleStart,
                    quarterEnd = schedule.scheduleEnd,
                )
            }

            else -> throw IllegalArgumentException("Неверный тип расписания")
        }
    }
}