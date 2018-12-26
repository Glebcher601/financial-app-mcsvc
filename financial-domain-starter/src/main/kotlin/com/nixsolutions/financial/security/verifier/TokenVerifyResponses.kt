package com.nixsolutions.financial.security.verifier

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
data class JwtVerifyResponse(val status: String, val reason: String? = null)

fun positiveResponse() =
    ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(JwtVerifyResponse(HttpStatus.ACCEPTED.name))

fun errorResponse(throwable: Throwable) =
    ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(JwtVerifyResponse(HttpStatus.FORBIDDEN.name, throwable.message))

