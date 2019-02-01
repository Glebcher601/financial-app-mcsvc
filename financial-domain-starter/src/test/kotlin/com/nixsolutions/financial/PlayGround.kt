package com.nixsolutions.financial

import reactor.core.publisher.Mono
import java.time.Duration

object PlayGround {
  @Throws(ClassNotFoundException::class)
  @JvmStatic
  fun main(args: Array<String>) {
    /*    String key = "message";
    Mono<String> r = Mono.just("Hello")
        .flatMap(s -> Mono.subscriberContext()
            .map(ctx -> s + " " + ctx.get(key)))
        .subscriberContext(ctx -> ctx.put(key, "World"));

    StepVerifier.create(r)
        .expectNext("Hello World")
        .verifyComplete();*/
    //filter enriches context
    val str = Mono.just("Value").subscriberContext { context -> context.put("Auth", "JWT1") }
    println("AFTER")
    Mono.subscriberContext().flatMap { context ->
      println(context)
      serviceMethod()
    }

  }

  fun serviceMethod(): Mono<String> {
    return Mono.delay(Duration.ofSeconds(1))
        .flatMap { delay -> Mono.just("User1") }
  }
}
