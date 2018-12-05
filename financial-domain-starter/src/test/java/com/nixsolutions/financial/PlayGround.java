package com.nixsolutions.financial;

import java.time.Duration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PlayGround
{
  public static void main(String[] args)
  {
    String key = "message";
    Mono<String> r = Mono.just("Hello")
        .flatMap(s -> Mono.subscriberContext()
            .map(ctx -> s + " " + ctx.get(key)))
        .subscriberContext(ctx -> ctx.put(key, "World"));

    StepVerifier.create(r)
        .expectNext("Hello World")
        .verifyComplete();
  }

  public Mono<String> serviceMethod()
  {
    return Mono.delay(Duration.ofSeconds(1))
        .flatMap(delay -> Mono.just("User1"));
  }
}
