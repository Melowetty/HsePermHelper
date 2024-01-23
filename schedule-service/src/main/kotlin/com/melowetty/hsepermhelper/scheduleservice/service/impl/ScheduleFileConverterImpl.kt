package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import com.melowetty.hsepermhelper.scheduleservice.service.BaseScheduleParsingStrategy
import com.melowetty.hsepermhelper.scheduleservice.service.ScheduleFileConverter
import com.melowetty.hsepermhelper.scheduleservice.utils.ParserUtils
import com.melowetty.hsepermhelper.scheduleservice.utils.ParserUtils.Companion.getWeekInfo
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.springframework.stereotype.Component
import java.io.InputStream
import java.lang.IllegalArgumentException
import java.util.*

@Component
class ScheduleFileConverterImpl(
    private val scheduleParsingStrategiesInstances: List<BaseScheduleParsingStrategy>
): ScheduleFileConverter {
    val scheduleParsingStrategies: MutableMap<ScheduleType, BaseScheduleParsingStrategy> = mutableMapOf()
    init {
        scheduleParsingStrategiesInstances.forEach { strategy ->
            strategy.getCompatibleScheduleTypes().forEach {
                scheduleParsingStrategies[it] = strategy
            }
        }
    }
    override fun convertInputStreamToScheduleDto(uuid: UUID, inputStream: InputStream): BaseScheduleDto? {
        val workbook = getWorkbook(inputStream)
        val scheduleInfo = getWeekInfo(
            ParserUtils.getValue(
                workbook.getSheetAt(2),
                workbook.getSheetAt(1).getRow(1).getCell(3))
        )
        if (scheduleInfo.scheduleStartDate == null || scheduleInfo.scheduleEndDate == null) {
            return null
        }
        return scheduleParsingStrategies[scheduleInfo.scheduleType]?.parseSchedule(uuid, workbook, scheduleInfo)
            ?: throw IllegalArgumentException("Парсер для такого типа расписания не найден!")
    }

    private fun getWorkbook(inputStream: InputStream): Workbook {
        return WorkbookFactory.create(inputStream)
    }
}