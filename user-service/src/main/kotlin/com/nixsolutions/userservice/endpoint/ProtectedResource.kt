package com.nixsolutions.userservice.endpoint

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ProtectedResource {

  @GetMapping(path = ["/protected"])
  @PreAuthorize("hasAuthority('admin_permission')")
  fun protected() = Mono.just("PROTECTED")
}

/*@Configuration
class ProtectedResource {

  @Bean
  fun protectedResourceRoute(): RouterFunction<ServerResponse> =
      RouterFunctions.route()
          .GET("/protected")
          @PreAuthorize("hasAuthority('admin_permission')")
          { req -> ServerResponse.ok().body(BodyInserters.fromObject("protected"))}
          .build()
}*/
