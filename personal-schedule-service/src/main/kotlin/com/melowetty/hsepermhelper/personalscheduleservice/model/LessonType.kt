package com.melowetty.hsepermhelper.personalscheduleservice.model

enum class LessonType(val type: String) {
    LECTURE("Лекция"),
    SEMINAR("Семинар"),
    EXAM("Экзамен"),
    INDEPENDENT_EXAM("Независимый экзамен"),
    TEST("Зачёт"),
    PRACTICE("Практика"),
    COMMON_MINOR("Майнор"),
    MINOR("Майнор"),
    COMMON_ENGLISH("Английский"),
    ENGLISH("Английский"),
    STATEMENT("Ведомость"),
    ICC("МКД"),
    UNDEFINED_AED("ДОЦ по выбору"),
    AED("ДОЦ"),
    EVENT("Мероприятие");
}