package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.ScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.LessonMapper
import com.melowetty.hsepermhelper.scheduleservice.mapper.ScheduleMapper
import com.melowetty.hsepermhelper.scheduleservice.model.*
import com.melowetty.hsepermhelper.scheduleservice.repository.GroupRepository
import com.melowetty.hsepermhelper.scheduleservice.repository.LessonRepository
import com.melowetty.hsepermhelper.scheduleservice.repository.ScheduleRepository
import com.melowetty.hsepermhelper.scheduleservice.repository.SubjectRepository
import com.melowetty.hsepermhelper.scheduleservice.service.LessonService
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleFileConverter
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayInputStream
import java.time.LocalDate
import java.time.LocalTime
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

    override fun getSchedulesByGroupId(groupId: Long, lang: Language): List<ScheduleDto> {
        val lessonModelMapper: LessonMapper = when(lang) {
            Language.RUSSIAN -> {
                russianLessonMapper
            }

            Language.ENGLISH -> {
                englishLessonMapper
            }
        }
        val schedules = scheduleRepository.getSchedulesByGroupId(groupId)
        return schedules.map { ScheduleMapper(lessonModelMapper).toDto(it) }
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