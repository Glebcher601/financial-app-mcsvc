package com.nixsolutions.financial.discovery;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

public interface ServiceRegistryAware
{
  default WebClient getWebClient(String uri)
  {
    return WebClient.builder()
        .baseUrl(uri)
        .build();
  }

  default String composePath(String... pathElements)
  {
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(getServiceUrl());
    uriComponentsBuilder.pathSegment(pathElements);
    return uriComponentsBuilder.toUriString();
  }

  String getServiceUrl();
}