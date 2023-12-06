package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.ScheduleMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.repository.ScheduleRepository
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    @Qualifier("lesson_ru_mapper")
    private val russianLessonMapper: LessonMapper,
    @Qualifier("lesson_en_mapper")
    private val englishLessonMapper: LessonMapper,
): ScheduleService {
    override fun findAllSchedules(lang: Language): List<ScheduleDto> {
        val lessonModelMapper: LessonMapper = when(lang) {
            Language.RUSSIAN -> {
                russianLessonMapper
            }

            Language.ENGLISH -> {
                englishLessonMapper
            }
        }
        return scheduleRepository.findAll().map { ScheduleMapper(lessonModelMapper).toDto(it) }
    }
}