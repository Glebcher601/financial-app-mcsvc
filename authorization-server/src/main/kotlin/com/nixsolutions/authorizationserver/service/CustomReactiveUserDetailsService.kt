package com.nixsolutions.authorizationserver.service

import com.nixsolutions.authorizationserver.security.CustomUserDetails
import com.nixsolutions.authorizationserver.security.User
import com.nixsolutions.financial.discovery.ServiceRegistry
import com.nixsolutions.financial.discovery.ServiceRegistry.Services.USERS
import com.nixsolutions.financial.discovery.ServiceRegistryAware
import com.nixsolutions.financial.security.properties.SystemJwtAuthenticationHolder
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomReactiveUserDetailsService(var serviceRegistry: ServiceRegistry,
                                       var jwtAuthenticationHolder: SystemJwtAuthenticationHolder) :
    ReactiveUserDetailsService, ServiceRegistryAware {

  override fun findByUsername(userName: String): Mono<UserDetails> =
      jwtAuthenticationHolder.getSystemWebClient(composePath("users", "byLogin", userName))
          .get().retrieve()
          .bodyToMono(User::class.java)
          .map { CustomUserDetails(it) }

  override fun getServiceUrl() = serviceRegistry.getServiceUrl(USERS)
}