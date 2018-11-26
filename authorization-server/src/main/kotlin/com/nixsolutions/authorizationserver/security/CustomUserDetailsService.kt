package com.nixsolutions.authorizationserver.security

import com.nixsolutions.authorizationserver.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userService: UserService;

    override fun loadUserByUsername(userName: String): UserDetails {
        val user = userService.findUserByLogin(userName);
        return CustomUserPrincipal(user.block() ?: throw RuntimeException("User not found"));
    }
}