package com.nixsolutions.financial.security.verifier

import com.nixsolutions.financial.security.SecurityProperties
import com.nixsolutions.financial.security.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.apache.commons.lang3.StringUtils.isEmpty
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Component

@Component
@ConditionalOnBean(SecurityProperties::class)
class SecretAwareJwtVerifier(private val securityProperties: SecurityProperties) : JwtVerifier {

  @Throws(InvalidTokenException::class)
  override fun parseToken(token: String?): Claims {
    if (isEmpty(token)) {
      throw InvalidTokenException("Token is empty!")
    }

    try {
      return Jwts.parser()
          .setSigningKey(securityProperties.jwtSecret?.toByteArray())
          .parseClaimsJws(token)
          .body
    } catch (ex: Exception) {
      throw InvalidTokenException(ex.message ?: "Wrong token", ex)
    }
  }
}
