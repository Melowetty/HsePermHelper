package com.melowetty.hsepermhelper.apigateway.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.melowetty.hsepermhelper.apigateway.model.Response
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction
import org.springframework.core.Ordered
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class ResponseGlobalConverter: GlobalFilter, Ordered {
    @Autowired
    private val modifyResponseBodyGatewayFilterFactory: ModifyResponseBodyGatewayFilterFactory? = null

    @Autowired
    private val bodyRewrite: BodyRewrite? = null

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val delegate = modifyResponseBodyGatewayFilterFactory!!.apply(
            ModifyResponseBodyGatewayFilterFactory.Config()
                .setRewriteFunction(
                    ByteArray::class.java,
                    ByteArray::class.java, bodyRewrite
                )
        )
        return delegate.filter(exchange, chain)
    }

    override fun getOrder(): Int {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1
    }
}

@Component
class BodyRewrite : RewriteFunction<ByteArray?, ByteArray?> {
    override fun apply(exchange: ServerWebExchange, body: ByteArray?): Publisher<ByteArray?> {
        val originalBody = if (body == null) "" else String(body)
        if (exchange.response.headers.contentType == null || exchange.response.headers.contentType?.includes(MediaType.APPLICATION_JSON)?.not() == true) {
            return Mono.just(originalBody.toByteArray())
        }
        val originalBodyAsJson = jacksonObjectMapper().readValue<Any>(originalBody)
        val response = Response(originalBodyAsJson, false)
        val jsonByteArray = jacksonObjectMapper().writeValueAsString(response).toByteArray()
        return Mono.just(jsonByteArray)
    }
}