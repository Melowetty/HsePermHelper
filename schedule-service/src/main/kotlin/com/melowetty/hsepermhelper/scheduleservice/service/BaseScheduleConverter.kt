package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.ScheduleType
import com.melowetty.hsepermhelper.scheduleservice.utils.ParserUtils
import org.apache.poi.ss.usermodel.Workbook
import java.util.*

interface BaseScheduleConverter {
    fun getSchedule(uuid: UUID, workbook: Workbook, scheduleInfo: ParserUtils.ParsedScheduleInfo): BaseScheduleDto?
    fun getScheduleTypes(): List<ScheduleType>
}