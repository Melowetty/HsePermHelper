package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.model.BaseLesson

interface LessonService {
    fun save(lesson: BaseLesson): BaseLesson
}