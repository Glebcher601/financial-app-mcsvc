package com.nixsolutions.financial.security.config

import org.springframework.security.config.web.server.ServerHttpSecurity

data class HttpSecurityConfigurationHolder(val httpSecurity: ServerHttpSecurity)