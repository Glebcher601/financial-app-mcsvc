package com.nixsolutions.financial.discovery

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import java.util.*

@ConfigurationProperties(prefix = "services")
@Validated
class ServiceDiscoveryProperties {
  var list: List<ServiceRecord> = ArrayList();
}
