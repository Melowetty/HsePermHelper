package com.melowetty.hsepermhelper.scheduleservice.service

import com.melowetty.hsepermhelper.scheduleservice.model.Lesson

interface LessonService {
    fun save(lesson: Lesson): Lesson
}