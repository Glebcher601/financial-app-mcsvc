package com.nixsolutions.authorizationserver.service.impl

import com.nixsolutions.authorizationserver.security.User
import com.nixsolutions.authorizationserver.service.UserService
import com.nixsolutions.financial.discovery.ServiceRegistry
import com.nixsolutions.financial.discovery.ServiceRegistry.Services.USERS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private const val USERS_RESOURCE = "/users";
private const val BY_LOGIN_RESOURCE = "/byLogin";

@Service
class UserServiceImpl : UserService {

  override fun findUserById(id: Long): User? {
    return getWebClient(composePath("users", id.toString()))
        .get().retrieve()
        .bodyToMono(User::class.java)
        .block()
  }

  @Autowired
  private lateinit var serviceRegistry: ServiceRegistry;

  override fun findUserByLogin(login: String): User? {
    return getWebClient(composePath("users", "byLogin", login))
        .get().retrieve()
        .bodyToMono(User::class.java)
        .block();
  }

  override fun getServiceUrl(): String {
    //TODO OR RETURN DEFAULT FALLBACK URL
    return serviceRegistry.getServiceUrl(USERS);
  }
}