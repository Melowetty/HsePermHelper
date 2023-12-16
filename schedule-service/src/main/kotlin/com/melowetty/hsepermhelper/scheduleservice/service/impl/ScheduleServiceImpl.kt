package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.ScheduleMapper
import com.melowetty.hsepermhelper.scheduleservice.model.Language
import com.melowetty.hsepermhelper.scheduleservice.repository.ScheduleRepository
import com.melowetty.hsepermhelper.scheduleservice.service.LessonService
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleFileConverter
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayInputStream
import java.util.*

@Service
class ScheduleServiceImpl(
    private val scheduleRepository: ScheduleRepository,
    private val lessonService: LessonService,
    private val scheduleFileConverter: ScheduleFileConverter,
    @Qualifier("lesson_ru_mapper")
    private val russianLessonMapper: LessonMapper,
    @Qualifier("lesson_en_mapper")
    private val englishLessonMapper: LessonMapper,
): ScheduleService {
    override fun findAllSchedules(lang: Language): List<ScheduleDto> {
        return scheduleRepository.findAll().map { getScheduleMapperByLanguage(lang).toDto(it) }
    }

    override fun getSchedulesByGroupId(groupId: Long, lang: Language): List<ScheduleDto> {
        val schedules = scheduleRepository.getSchedulesByGroupId(groupId)
        return schedules.map { getScheduleMapperByLanguage(lang).toDto(it) }
    }

    override fun getSchedulesByProgrammeId(programmeId: Long, lang: Language): List<ScheduleDto> {
        val schedules = scheduleRepository.getSchedulesByProgrammeId(programmeId)
        return schedules.map { getScheduleMapperByLanguage(lang).toDto(it) }
    }

    private fun getScheduleMapperByLanguage(language: Language): ScheduleMapper {
        val lessonModelMapper: LessonMapper = when(language) {
            Language.RUSSIAN -> {
                russianLessonMapper
            }

            Language.ENGLISH -> {
                englishLessonMapper
            }
        }
        return ScheduleMapper(lessonModelMapper)
    }

    @Transactional
    override fun mockInputStream(base64: String) {
        val inputStream = ByteArrayInputStream(Base64.getDecoder().decode(base64))
        val schedule = scheduleFileConverter.convertInputStreamToScheduleDto(inputStream)
        if (schedule != null) {
            val scheduleEntity = ScheduleMapper(russianLessonMapper).toEntity(schedule)
            val savedLessons = scheduleEntity.lessons.map { lessonService.save(it) }
            scheduleRepository.saveAndFlush(scheduleEntity.copy(lessons = savedLessons))
        }
    }
}