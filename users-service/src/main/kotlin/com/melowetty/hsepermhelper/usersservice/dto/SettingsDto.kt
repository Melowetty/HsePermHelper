package com.melowetty.hsepermhelper.usersservice.dto

import com.melowetty.languagessupportlibrary.model.Language

data class SettingsDto(
    val language: Language,

    val otherServicesSettings: Map<String, List<Any>>? = null
//    @JsonIgnore
//    @Schema(description = "Включен ли удаленный календарь", example = "false")
//    val isEnabledRemoteCalendar: Boolean = false,
//
//    @Schema(description = "Включены ли уведомления о новом расписании", example = "true")
//    val isEnabledNewCommonScheduleNotifications: Boolean = true,
//
//    @Schema(description = "Включены ли уведомления о новом расписании на модуль", example = "false")
//    val isEnabledNewQuarterScheduleNotifications: Boolean = false,
)