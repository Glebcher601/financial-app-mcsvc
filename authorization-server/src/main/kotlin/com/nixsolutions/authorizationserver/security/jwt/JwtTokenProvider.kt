package com.nixsolutions.authorizationserver.security.jwt

import com.nixsolutions.authorizationserver.security.CustomUserDetails
import io.jsonwebtoken.*
import org.apache.commons.lang3.StringUtils.isEmpty
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}")
    private val jwtSecret: String,
    @Value("\${security.jwt.expiresIn}")
    private val expiresIn: Int) {

  private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

  fun generateToken(authentication: Authentication): String {

    val userPrincipal = authentication.principal as CustomUserDetails

    val now = Date()
    val expiryDate = Date(now.getTime() + expiresIn)

    return Jwts.builder()
        .setSubject(java.lang.Long.toString(userPrincipal.getId()))
        .setIssuedAt(Date())
        .setExpiration(expiryDate)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact()
  }

  fun getUserIdFromJWT(token: String): Long {
    val claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .body

    return java.lang.Long.parseLong(claims.subject)
  }

  fun validateToken(authToken: String): Boolean {
    if (isEmpty(authToken)) {
      return false;
    }


    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
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