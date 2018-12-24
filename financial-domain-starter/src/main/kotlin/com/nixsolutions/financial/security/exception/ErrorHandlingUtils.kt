package com.nixsolutions.financial.security.exception

import org.springframework.core.ResolvableType
import org.springframework.core.codec.Hints
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.*

fun getCustomErrorAttributes(message: String, request: ServerHttpRequest): Map<String, Any> {
  val errorAttributes = HashMap<String, Any>()
  errorAttributes["timestamp"] = Date()
  errorAttributes["message"] = message
  errorAttributes["status"] = HttpStatus.UNAUTHORIZED.value()
  errorAttributes["path"] = request.path.toString()

  return errorAttributes;
}

fun writeErrorResponse(exchange: ServerWebExchange, errorAttributes: Map<String, Any>) =
    exchange.response.writeWith(
        Jackson2JsonEncoder().encode(
            Mono.just(errorAttributes),
            exchange.response.bufferFactory(),
            ResolvableType.forInstance(errorAttributes),
            MediaType.APPLICATION_JSON_UTF8,
            Hints.from(Hints.LOG_PREFIX_HINT, exchange.logPrefix)))