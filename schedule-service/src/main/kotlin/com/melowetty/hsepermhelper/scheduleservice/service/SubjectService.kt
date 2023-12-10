package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.model.Subject

interface SubjectService {
    fun save(subject: Subject): Subject
}