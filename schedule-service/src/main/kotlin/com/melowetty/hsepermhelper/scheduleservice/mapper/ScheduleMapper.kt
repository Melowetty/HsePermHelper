package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.BaseSchedule

interface ScheduleMapper : Mappable<BaseSchedule, BaseScheduleDto>, Translatable