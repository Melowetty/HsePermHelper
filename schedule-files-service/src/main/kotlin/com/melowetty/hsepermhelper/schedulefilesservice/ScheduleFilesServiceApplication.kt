package com.melowetty.hsepermhelper.schedulefilesservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class ScheduleFilesServiceApplication

fun main(args: Array<String>) {
    runApplication<ScheduleFilesServiceApplication>(*args)
}
