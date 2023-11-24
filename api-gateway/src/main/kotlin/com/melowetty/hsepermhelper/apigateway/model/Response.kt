package com.melowetty.hsepermhelper.apigateway.model

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("error", "response")
data class Response(
    val response: Any,
    val error: Boolean = false,
)
