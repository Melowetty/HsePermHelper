package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.Lesson
import org.springframework.data.jpa.repository.JpaRepository

interface LessonRepository: JpaRepository<Lesson, Long>