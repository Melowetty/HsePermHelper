package com.melowetty.hsepermhelper.scheduleservice.service.impl

import com.melowetty.hsepermhelper.scheduleservice.model.BaseLesson
import com.melowetty.hsepermhelper.scheduleservice.repository.LessonRepository
import com.melowetty.hsepermhelper.scheduleservice.service.GroupService
import com.melowetty.hsepermhelper.scheduleservice.service.LessonService
import com.melowetty.hsepermhelper.scheduleservice.service.SubjectService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LessonServiceImpl(
    private val lessonRepository: LessonRepository,
    private val subjectService: SubjectService,
    private val groupService: GroupService
): LessonService {
    @Transactional
    override fun save(lesson: BaseLesson): BaseLesson {
        val subject = subjectService.save(lesson.subject)
        val group = groupService.save(lesson.group)
        lesson.subject = subject
        lesson.group = group
        return lessonRepository.save(lesson)
    }
}