package com.melowetty.hsepermhelper.scheduleservice.mapper.ru

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.dto.QuarterScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.dto.WeekScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.ScheduleMapper
import com.melowetty.hsepermhelper.scheduleservice.model.BaseSchedule
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.model.QuarterSchedule
import com.melowetty.hsepermhelper.scheduleservice.model.WeekSchedule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ScheduleRuMapper(
    @Qualifier("lesson_ru_mapper")
    private val lessonMapper: LessonMapper
) : ScheduleMapper {
    override fun toEntity(dto: BaseScheduleDto): BaseSchedule {
        when (dto) {
            is WeekScheduleDto -> {
                return WeekSchedule(
                    systemId = dto.uuid,
                    weekNumber = dto.weekNumber,
                    lessons = dto.lessons.map { lessonMapper.toEntity(it) },
                    weekStart = dto.scheduleStart,
                    weekEnd = dto.scheduleEnd,
                    scheduleType = dto.scheduleType,
                )
            }
            is QuarterScheduleDto -> {
                return QuarterSchedule(
                    systemId = dto.uuid,
                    quarterNum = dto.quarterNumber,
                    lessons = dto.lessons.map { lessonMapper.toEntity(it) },
                    quarterStart = dto.scheduleStart,
                    quarterEnd = dto.scheduleEnd,
                )
            }

            else -> throw IllegalArgumentException("Неверный тип расписания")
        }
    }

    override fun toDto(entity: BaseSchedule): BaseScheduleDto {
        when (entity) {
            is WeekSchedule -> {
                return WeekScheduleDto(
                    uuid = entity.systemId,
                    weekNumber = entity.weekNumber,
                    lessons = entity.lessons.map { lessonMapper.toDto(entity.scheduleType, it) },
                    weekStart = entity.scheduleStart,
                    weekEnd = entity.scheduleEnd,
                    scheduleType = entity.scheduleType
                )
            }
            is QuarterSchedule -> {
                return QuarterScheduleDto(
                    uuid = entity.systemId,
                    quarterNumber = entity.quarterNum,
                    lessons = entity.lessons.map { lessonMapper.toDto(entity.scheduleType, it) },
                    quarterStart = entity.scheduleStart,
                    quarterEnd = entity.scheduleEnd,
                )
            }

            else -> throw IllegalArgumentException("Неверный тип расписания")
        }
    }

    override fun getLanguage(): Language {
        return Language.RUSSIAN
    }
}