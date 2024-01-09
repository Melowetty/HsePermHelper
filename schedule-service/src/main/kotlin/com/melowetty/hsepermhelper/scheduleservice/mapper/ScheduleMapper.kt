package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.BaseScheduleDto
import com.melowetty.hsepermhelper.scheduleservice.model.BaseSchedule
import com.melowetty.languagessupportlibrary.model.Translatable
import com.melowetty.mapperlibrary.Mappable

interface ScheduleMapper : Mappable<BaseSchedule, BaseScheduleDto>, Translatable