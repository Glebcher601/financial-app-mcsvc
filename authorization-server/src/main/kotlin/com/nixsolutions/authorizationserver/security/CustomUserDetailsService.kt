package com.nixsolutions.authorizationserver.security

import com.nixsolutions.authorizationserver.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
@Deprecated("")
class CustomUserDetailsService : UserDetailsService, UserDetailsByIdSearch {

  @Autowired
  private lateinit var userService: UserService;

  override fun loadUserByUsername(userName: String): UserDetails {
    val user = userService.findUserByLogin(userName);
    return CustomUserDetails(user ?: throw RuntimeException("User not found"));
  }

  override fun findUserById(id: Long): User? {
    val user = userService.findUserById(id);
    return user;
  }
}