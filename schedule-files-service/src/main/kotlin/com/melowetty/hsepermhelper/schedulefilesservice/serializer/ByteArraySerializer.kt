package com.melowetty.hsepermhelper.schedulefilesservice.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.util.*

class ByteArraySerializer: JsonSerializer<ByteArray>() {
    override fun serialize(value: ByteArray, gen: JsonGenerator, serializers: SerializerProvider) {
        val str = Base64.getEncoder().encodeToString(value)
        gen.writeString(str)
    }
}