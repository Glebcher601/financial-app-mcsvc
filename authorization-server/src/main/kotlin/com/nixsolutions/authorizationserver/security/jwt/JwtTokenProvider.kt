package com.nixsolutions.authorizationserver.security.jwt

import com.nixsolutions.authorizationserver.security.CustomUserDetails
import com.nixsolutions.financial.security.SecurityConstants
import com.nixsolutions.financial.security.SecurityProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import java.util.stream.Collectors.toList


@Component
class JwtTokenProvider(@Autowired val securityProperties: SecurityProperties) {

  private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

  fun generateToken(authentication: Authentication): String {
    val userPrincipal = authentication.principal as CustomUserDetails
    val expiryDate = getExpiryDate()

    val permissions = userPrincipal.authorities.stream()
        .map { it.authority }
        .collect(toList())

    return Jwts.builder()
        .setSubject(userPrincipal.getId().toString())
        .claim(SecurityConstants.NAME, userPrincipal.username)
        .claim(SecurityConstants.PERMISSIONS, permissions)
        .setIssuedAt(Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, securityProperties.jwtSecret?.toByteArray())
        .compact()
  }

  fun getExpiryDate() = Date(Date().time + Duration.ofSeconds(securityProperties.expiresIn?.toLong()!!).toMillis())

}