package com.nixsolutions.userservice.misc

import reactor.core.publisher.Mono
import java.util.*


fun <T> Optional<T>.unwrap(): T? = orElse(null)

fun <T> async(callable: () -> T): Mono<T> = Mono.fromCallable(callable)
