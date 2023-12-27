package com.melowetty.hsepermhelper.scheduleservice.repository

import com.melowetty.hsepermhelper.scheduleservice.model.BaseLesson
import org.springframework.data.jpa.repository.JpaRepository

interface LessonRepository: JpaRepository<BaseLesson, Long>