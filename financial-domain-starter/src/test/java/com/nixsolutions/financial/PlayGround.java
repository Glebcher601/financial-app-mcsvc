package com.nixsolutions.financial;

import java.time.Duration;
import reactor.core.publisher.Mono;

public class PlayGround
{
  public static void main(String[] args) throws ClassNotFoundException
  {
/*    String key = "message";
    Mono<String> r = Mono.just("Hello")
        .flatMap(s -> Mono.subscriberContext()
            .map(ctx -> s + " " + ctx.get(key)))
        .subscriberContext(ctx -> ctx.put(key, "World"));

    StepVerifier.create(r)
        .expectNext("Hello World")
        .verifyComplete();*/
    //filter enriches context
    Mono<String> str = Mono.just("Value").subscriberContext(context -> context.put("Auth", "JWT1"));
    System.out.println("AFTER");
    Mono.subscriberContext().flatMap(context ->
    {
      System.out.println(context);
      return serviceMethod();
    });

  }

  public static Mono<String> serviceMethod()
  {
    return Mono.delay(Duration.ofSeconds(1))
        .flatMap(delay -> Mono.just("User1"));
  }
}
