package com.melowetty.hsepermhelper.schedulefilesservice.repository.impl

import com.melowetty.hsepermhelper.schedulefilesservice.model.ScheduleFile
import com.melowetty.hsepermhelper.schedulefilesservice.repository.ScheduleFileRepository
import org.jsoup.Jsoup
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.*
import kotlin.io.path.extension
import kotlin.io.path.inputStream
import kotlin.io.path.isRegularFile


@Repository
class ScheduleFileRepositoryImpl(
    environment: Environment,
): ScheduleFileRepository {
    private final val scheduleFilesPathString = environment["app.schedule-files.path"] ?: "/schedules"
    private final val scheduleBaseUrl = environment["app.schedule-files.base-url"] ?: "http://students.perm.hse.ru/timetable"
    private final val scheduleBaseDownloadUrl = environment["app.schedule-files.base-download-url"] ?: "http://students.perm.hse.ru"

    private final val scheduleFilesPath = Path.of(scheduleFilesPathString).toAbsolutePath().normalize()

    private var schedules: SortedSet<ScheduleFile> = sortedSetOf()

    init {
        try {
            if (Files.exists(scheduleFilesPath).not()) {
                Files.createDirectories(scheduleFilesPath)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error("Не удалось создать директорию для хранения расписаний.")
        }
        Files.walk(scheduleFilesPath).forEach {
            if (it.fileName.toString().contains("schedule").not()) return@forEach
            if (it.isRegularFile()) {
                val inputStream = ByteArrayInputStream(it.inputStream().readAllBytes())
                try {
                    val uuid = it.fileName.toString().split("_")[1].split(".")[0]
                    schedules.add(ScheduleFile(inputStream.readAllBytes(), it.fileName.extension, UUID.fromString(uuid)))
                } catch (_: Exception) {}
            }
        }
    }

    override fun getScheduleFiles(): List<ScheduleFile> {
        return schedules.toList()
    }

    @Scheduled(fixedRateString = "\${app.schedule-files.checking-rate}")
    fun fetchScheduleFilesAsInputStream() {
        val response = Jsoup.connect(scheduleBaseUrl).get()
        val elements = response.select(".content__inner.post__text p")
        val files = sortedSetOf<ScheduleFile>()
        for (element in elements) {
            if (element.html().contains("Бакалавриат")) continue
            if(element.html().lowercase().contains("английский")) continue
            if(element.html().contains("Магистратура")) break
            val childLink = element.select("a")
            if (childLink.isEmpty()) continue
            val link = childLink.attr("href")
            if (link.isEmpty()) continue
            val inputStream = downloadSchedulesAsInputStream(path = link)
            if (inputStream != null) {
                val fileExtension = link.split('.').last()
                files.add(ScheduleFile(inputStream.readAllBytes(), fileExtension, UUID.randomUUID()))
            }
        }
        checkChanges(files)
        schedules = files.map { scheduleFile ->
            schedules.find { it == scheduleFile } ?: scheduleFile
        }.toSortedSet()
    }

    private fun checkChanges(files: SortedSet<ScheduleFile>) {
        if(files != schedules) {
            val deleted = mutableListOf<ScheduleFile>()
            for(file in schedules) {
                if (files.contains(file).not()) {
                    deleted.add(file)
                }
            }
            val addedOrUpdated = mutableListOf<ScheduleFile>()
            for(file in files) {
                if(schedules.contains(file).not()) {
                    addedOrUpdated.add(file)
                }
            }
            // send deleted files kafka event
            // send added or updated files kafka event
            createNewScheduleFiles(files)
        }
    }

    private fun clearAllScheduleFiles() {
        Files.walk(scheduleFilesPath).forEach {
            if (it.isRegularFile().not()) return@forEach
            if (it.fileName.toString().contains("schedule").not()) return@forEach
            try {
                Files.deleteIfExists(it)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Произошла ошибка при удалении файлов из директории!")
            }
        }
    }

    private fun createNewScheduleFiles(files: SortedSet<ScheduleFile>) {
        clearAllScheduleFiles()
        files.forEach {
            try {
                Files.copy(ByteArrayInputStream(it.bytes), scheduleFilesPath.resolve("schedule_${it.uuid}.${it.extension}"), StandardCopyOption.REPLACE_EXISTING)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Произошла ошибка при создании файлов расписания!")
            }
        }
    }

    private fun downloadSchedulesAsInputStream(path: String): InputStream? {
        return try {
            val urlParts = path.split("/data")
            URL("$scheduleBaseDownloadUrl/data${urlParts[1]}").openStream().readAllBytes().inputStream()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}