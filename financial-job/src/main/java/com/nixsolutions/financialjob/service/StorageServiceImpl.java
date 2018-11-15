package com.nixsolutions.financialjob.service;

import com.nixsolutions.financial.discovery.ServiceRegistry;
import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import static com.nixsolutions.financial.discovery.ServiceRegistry.Services.STORAGE;

@Component
public class StorageServiceImpl implements StorageService
{
  private static final String STOCK_SNAPSHOTS_RESOURCE = "/stockSnapshots";

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Override
  public void save(SymbolStockSnapshots symbolStockSnapshots)
  {
    getWebClient().post()
        .body(BodyInserters.fromObject(symbolStockSnapshots))
        .exchange()
        .map(ClientResponse::statusCode)
        .blockOptional()
        .filter(HttpStatus::is2xxSuccessful)
        .orElseThrow(RuntimeException::new);
  }

  private WebClient getWebClient()
  {
    String resourceUrl = UriComponentsBuilder.fromHttpUrl(serviceRegistry.getServiceUrl(STORAGE))
        .path(STOCK_SNAPSHOTS_RESOURCE)
        .toUriString();
    return WebClient.builder()
        .baseUrl(resourceUrl)
        .build();
  }
}
