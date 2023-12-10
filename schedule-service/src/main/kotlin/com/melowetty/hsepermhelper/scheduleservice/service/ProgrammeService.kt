package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.model.Programme

interface ProgrammeService {
    fun save(programme: Programme): Programme
}