package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.mapper.MapperWithLanguage
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
    scheduleMappers: List<ScheduleMapper>,
    @Qualifier("scheduleRuMapper")
    private val ruScheduleMapper: ScheduleMapper
): ScheduleService {
    val scheduleMapper: Map<Language, ScheduleMapper> = MapperWithLanguage.getMapWithKeyByLanguage(scheduleMappers)

    override fun findAllSchedules(lang: Language): List<BaseScheduleDto> {
        return scheduleRepository.findAll().map { getScheduleMapperByLanguage(lang).toDto(it) }
    }

    override fun getSchedulesByGroupId(groupId: Long, lang: Language): List<BaseScheduleDto> {
        val schedules = scheduleRepository.getSchedulesByGroupId(groupId)
        return schedules.map { getScheduleMapperByLanguage(lang).toDto(it) }
    }

    override fun getSchedulesByProgrammeId(programmeId: Long, lang: Language): List<BaseScheduleDto> {
        val schedules = scheduleRepository.getSchedulesByProgrammeId(programmeId)
        return schedules.map { getScheduleMapperByLanguage(lang).toDto(it) }
    }

    private fun getScheduleMapperByLanguage(language: Language): ScheduleMapper {
        return scheduleMapper.getOrDefault(language, ruScheduleMapper)
    }

    @Transactional
    override fun mockInputStream(base64: String) {
        val inputStream = ByteArrayInputStream(Base64.getDecoder().decode(base64))
        val schedule = scheduleFileConverter.convertInputStreamToScheduleDto(UUID.randomUUID(), inputStream)
        if (schedule != null) {
            val scheduleEntity = ruScheduleMapper.toEntity(schedule)
            val savedLessons = scheduleEntity.lessons.map { lessonService.save(it) }
            scheduleEntity.lessons = savedLessons
            scheduleRepository.saveAndFlush(scheduleEntity)
        }
    }
}