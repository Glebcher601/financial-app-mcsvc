package com.nixsolutions.financial.discovery

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder

interface ServiceRegistryAware {

  fun getServiceUrl(): String;

  @JvmDefault
  fun getWebClient(uri: String): WebClient {
    return WebClient.builder()
        .baseUrl(uri)
        .build()
  }

  @JvmDefault
  fun composePath(vararg pathElements: String): String {
    val uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(getServiceUrl())
    uriComponentsBuilder.pathSegment(*pathElements)
    return uriComponentsBuilder.toUriString()
  }
}