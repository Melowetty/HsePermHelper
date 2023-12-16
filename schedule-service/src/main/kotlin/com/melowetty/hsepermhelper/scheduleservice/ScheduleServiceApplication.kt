package com.melowetty.hsepermhelper.scheduleservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class ScheduleServiceApplication

fun main(args: Array<String>) {
    runApplication<ScheduleServiceApplication>(*args)
}
