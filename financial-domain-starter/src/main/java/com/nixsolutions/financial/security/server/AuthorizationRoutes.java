package com.nixsolutions.financial.security.server;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.nixsolutions.financial.security.verifier.JwtVerifier;

public class AuthorizationRoutes
{
  @Bean
  RouterFunction<ServerResponse> obtainTokenRoute()
  {
    return route(GET("/obtainToken"), req -> ServerResponse.ok().body(BodyInserters.fromObject("Fine")));
  }

  @Bean
  RouterFunction<ServerResponse> verifyTokenRoute(JwtVerifier jwtVerifier)
  {
    return route(GET("/verifyToken"), req -> ServerResponse.ok().body(BodyInserters.fromObject("Fine")));
  }
}
