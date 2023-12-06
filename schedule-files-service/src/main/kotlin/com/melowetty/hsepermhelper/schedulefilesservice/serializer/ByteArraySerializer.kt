package com.melowetty.hsepermhelper.schedulefilesservice.serializer

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class ByteArraySerializer: JsonSerializer<ByteArray>() {
    override fun serialize(value: ByteArray, gen: JsonGenerator, serializers: SerializerProvider) {
        val str = value.toString(Charsets.UTF_8)
        gen.writeString(str)
    }
}