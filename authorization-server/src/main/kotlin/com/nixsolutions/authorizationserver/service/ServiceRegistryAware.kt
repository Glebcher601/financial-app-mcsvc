package com.nixsolutions.authorizationserver.service

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder

interface ServiceRegistryAware {
  fun getWebClient(uri: String): WebClient {
    return WebClient.builder()
        .baseUrl(uri)
        .build()
  }

  fun composePath(vararg pathElements: String): String {
    val uri = UriComponentsBuilder.fromHttpUrl(getServiceUrl());
    pathElements.forEachIndexed { _, pathElement -> uri.path(pathElement) }
    return uri.toUriString()
  }

  fun getServiceUrl(): String
}