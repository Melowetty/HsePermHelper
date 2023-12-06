package com.melowetty.hsepermhelper.schedulefilesservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.melowetty.hsepermhelper.schedulefilesservice.serializer.ByteArraySerializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.scheduling.annotation.EnableScheduling

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
class ScheduleFilesServiceApplication

fun main(args: Array<String>) {
    runApplication<ScheduleFilesServiceApplication>(*args)
}

@Bean
@Primary
fun objectMapper(): ObjectMapper {
    return Jackson2ObjectMapperBuilder()
        .serializers(ByteArraySerializer())
        .build()
}
