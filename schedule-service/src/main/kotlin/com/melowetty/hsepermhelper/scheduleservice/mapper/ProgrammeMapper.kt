package com.melowetty.hsepermhelper.scheduleservice.mapper

import com.melowetty.hsepermhelper.scheduleservice.dto.ProgrammeDto
import com.melowetty.hsepermhelper.scheduleservice.model.Programme
import com.melowetty.languagessupportlibrary.model.Translatable
import com.melowetty.mapperlibrary.MappableToDto

interface ProgrammeMapper : MappableToDto<Programme, ProgrammeDto>, Translatable