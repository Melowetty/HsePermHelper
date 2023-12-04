package com.melowetty.hsepermhelper.schedulefilesservice.model

import com.melowetty.hsepermhelper.schedulefilesservice.utils.FileUtils
import java.io.InputStream
import java.util.*

data class ScheduleFile(
    val inputStream: InputStream,
    val extension: String,
    val uuid: UUID = UUID.randomUUID(),
    val hashcode: String = FileUtils.getHash(inputStream),
) : Comparable<ScheduleFile> {
    override fun compareTo(other: ScheduleFile): Int {
        return hashcode.compareTo(other.hashcode)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScheduleFile

        return hashcode == other.hashcode
    }

    override fun hashCode(): Int {
        return hashcode.hashCode()
    }

    override fun toString(): String {
        return "$hashcode $uuid $extension"
    }
}