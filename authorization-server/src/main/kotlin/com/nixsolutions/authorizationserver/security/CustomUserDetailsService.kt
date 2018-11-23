package com.nixsolutions.authorizationserver.security

import com.nixsolutions.authorizationserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var userRepository: UserRepository;

    override fun loadUserByUsername(userName: String): UserDetails {
        val user = userRepository.findByLogin(userName);
        return CustomUserPrincipal(user ?: throw RuntimeException("User not found"));
    }
}