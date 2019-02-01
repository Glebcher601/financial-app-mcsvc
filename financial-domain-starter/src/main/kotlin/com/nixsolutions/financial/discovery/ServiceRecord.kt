package com.nixsolutions.financial.discovery

import org.hibernate.validator.constraints.NotBlank
import javax.validation.constraints.NotNull

class ServiceRecord {
  /**
   * Defines the name of a service
   */
  @NotBlank
  var name: String? = null

  /**
   * Defines how to connect to service
   */
  @NotNull
  var resolution: DiscoveryType? = null

  /**
   * Version is not used for now
   */
  var version: String? = null

  /**
   * Self-explaining
   */
  var port = "8080"
}