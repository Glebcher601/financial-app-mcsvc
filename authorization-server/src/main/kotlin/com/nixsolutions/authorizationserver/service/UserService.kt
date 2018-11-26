package com.nixsolutions.authorizationserver.service

import com.nixsolutions.authorizationserver.security.User
import com.nixsolutions.financial.discovery.ServiceRegistry
import com.nixsolutions.financial.discovery.ServiceRegistry.Services.USERS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

private const val USERS_RESOURCE = "/users";
private const val BY_LOGIN_RESOURCE = "/byLogin";

@Service
class UserService {
  @Autowired
  private lateinit var serviceRegistry: ServiceRegistry;

  fun findUserByLogin(login: String): Mono<User> {
    val uri = UriComponentsBuilder.fromHttpUrl(serviceRegistry.getServiceUrl(USERS))
        .path(USERS_RESOURCE)
        .path(BY_LOGIN_RESOURCE)
        .path(login)
        .toUriString()
    return getWebClient(uri).get().retrieve().bodyToMono(User::class.java);
  }

  private fun getWebClient(uri: String): WebClient {
    return WebClient.builder()
        .baseUrl(uri)
        .build()
  }
}