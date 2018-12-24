package com.nixsolutions.financial.security.properties

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.WebClient

@ConfigurationProperties(prefix = "financial-domain.security")
@ConditionalOnProperty("financial-domain.security.systemToken")
class SystemJwtAuthenticationHolder {
  //@Value("financial-domain.security.systemToken")
  var systemToken: String? = ""

  fun getSystemWebClient(baseUrl: String) = WebClient.builder()
      .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer $systemToken")
      .baseUrl(baseUrl)
      .build()
}