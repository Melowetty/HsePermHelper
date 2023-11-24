package com.melowetty.hsepermhelper.apigateway.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono




@Component
class TelegramBotAuth(
    private val environment: Environment
): AbstractGatewayFilterFactory<TelegramBotAuth.Config>(Config::class.java) {
    class Config // empty class

    override fun apply(config: Config?): GatewayFilter {
        val privateKey = environment["app.security.private-key"] ?: ""
        val privateKeyHeader = environment["app.security.private-key-header"] ?: ""
        return if (privateKey.isBlank() || privateKeyHeader.isBlank()) GatewayFilter { exchange, chain ->  chain.filter(exchange) }
        else GatewayFilter { exchange, chain ->
            val userAgent = exchange.request.headers.getOrEmpty(HttpHeaders.USER_AGENT)
            val telegramBotUserAgent = environment["app.security.telegram-bot-user-agent"] ?: ""
            if (userAgent.isEmpty()
                || telegramBotUserAgent.isBlank()
                || userAgent.first().contains("/").not()
                || userAgent.first().split("/").first().trim() != telegramBotUserAgent) {
                return@GatewayFilter onError(exchange, "Доступ запрещен!", HttpStatus.FORBIDDEN)
            }
            val privateKeyFromHeader = exchange.request.headers.getOrEmpty(privateKeyHeader)
            if (privateKeyFromHeader.isEmpty()) {
                return@GatewayFilter onError(exchange, "Не указан заголовок для авторизации!", HttpStatus.UNAUTHORIZED)
            }
            if (privateKey != privateKeyFromHeader.first()) {
                return@GatewayFilter onError(exchange, "Неверный ключ авторизации!", HttpStatus.FORBIDDEN)
            }
            else {
                return@GatewayFilter chain.filter(exchange)
            }
        }
    }

    fun onError(exchange: ServerWebExchange, error: String, httpStatus: HttpStatus): Mono<Void> {
        val newResponse = exchange.response
        newResponse.setStatusCode(httpStatus)
        newResponse.headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        val responseBody = mutableMapOf<String, Any>()
        responseBody["message"] = error
        val bytes = jacksonObjectMapper().writeValueAsBytes(responseBody)
        val buffer = exchange.response.bufferFactory().wrap(bytes)
        return newResponse.writeWith(Mono.just(buffer))
    }
}