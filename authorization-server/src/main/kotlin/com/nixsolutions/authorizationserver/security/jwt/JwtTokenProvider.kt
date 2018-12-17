package com.nixsolutions.authorizationserver.security.jwt

import com.nixsolutions.authorizationserver.security.CustomUserDetails
import com.nixsolutions.financial.security.SecurityConstants
import com.nixsolutions.financial.security.SecurityProperties
import io.jsonwebtoken.*
import org.apache.commons.lang3.StringUtils.isEmpty
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.lang.Long.parseLong
import java.time.Duration
import java.util.*
import java.util.stream.Collectors.toList


@Component
class JwtTokenProvider(
    @Autowired
    val securityProperties: SecurityProperties) {

  private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

  fun generateToken(authentication: Authentication): String {

    val userPrincipal = authentication.principal as CustomUserDetails
    val expiryDate = Date(Date().time + Duration.ofSeconds(securityProperties.expiresIn.toLong()).toMillis())

    val roles = userPrincipal.authorities.stream().map(GrantedAuthority::getAuthority).collect(toList())

    return Jwts.builder()
        .setSubject(java.lang.Long.toString(userPrincipal.getId()))
        .claim(SecurityConstants.NAME, userPrincipal.username)
        .claim(SecurityConstants.ROLES, roles)
        .setIssuedAt(Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, securityProperties.jwtSecret.toByteArray())
        .compact()
  }

  fun getUserIdFromJWT(token: String): Long {
    val claims = Jwts.parser()
        .setSigningKey(securityProperties.jwtSecret)
        .parseClaimsJws(token)
        .body

    return parseLong(claims.subject)
  }

  fun validateToken(authToken: String): Boolean {
    if (isEmpty(authToken)) {
      return false;
    }

    try {
      Jwts.parser().setSigningKey(securityProperties.jwtSecret).parseClaimsJws(authToken)
      return true
    } catch (ex: SignatureException) {
      logger.error("Invalid JWT signature")
    } catch (ex: MalformedJwtException) {
      logger.error("Invalid JWT token")
    } catch (ex: ExpiredJwtException) {
      logger.error("Expired JWT token")
    } catch (ex: UnsupportedJwtException) {
      logger.error("Unsupported JWT token")
    } catch (ex: IllegalArgumentException) {
      logger.error("JWT claims string is empty.")
    }

    return false
  }
}