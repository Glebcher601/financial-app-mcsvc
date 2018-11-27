package com.nixsolutions.authorizationserver.security

import com.nixsolutions.authorizationserver.service.UserByIdSearch
import com.nixsolutions.authorizationserver.service.impl.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService : UserDetailsService, UserByIdSearch {

  @Autowired
  private lateinit var userService: UserServiceImpl;

  override fun loadUserByUsername(userName: String): UserDetails {
    val user = userService.findUserByLogin(userName);
    return CustomUserPrincipal(user ?: throw RuntimeException("User not found"));
  }

  override fun findUserById(id: Long): User? {
    val user = userService.findUserById(id);
    return user;
  }
}