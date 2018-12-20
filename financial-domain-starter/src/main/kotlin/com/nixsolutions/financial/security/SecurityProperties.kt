package com.nixsolutions.financial.security

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "financial-domain.security")
@ConditionalOnProperty("financial-domain.security.jwtSecret")
class SecurityProperties {
  var jwtSecret: String? = null

  var expiresIn: Int? = null

  var passwordHashingStrength: Int? = null
}
