package com.melowetty.hsepermhelper.usersservice.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.melowetty.hsepermhelper.usersservice.model.Language
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Настройки пользователя")
data class SettingsDto(
    @JsonIgnore
    val id: Long? = null,

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