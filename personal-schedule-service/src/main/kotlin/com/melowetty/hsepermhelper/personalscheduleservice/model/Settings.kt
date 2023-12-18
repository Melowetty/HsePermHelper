package com.melowetty.hsepermhelper.personalscheduleservice.model

data class Settings(
    val baseSubGroup: Int,

    val groupId: Long,

    val subGroupSelect: List<SubGroupSelect>,

    val includeQuarterSchedule: Boolean = false,

    val includeCommonEnglish: Boolean = false,

    val includeCommonMinor: Boolean = true,

//    @Column(name = "include_quarter_schedule", columnDefinition="BOOLEAN DEFAULT false")
//    val includeQuarterSchedule: Boolean = false,
//
//    @Column(name = "include_common_english", columnDefinition="BOOLEAN DEFAULT false")
//    val includeCommonEnglish: Boolean = false,
//
//    @Column(name = "include_common_minor", columnDefinition="BOOLEAN DEFAULT true")
//    val includeCommonMinor: Boolean = true,
)