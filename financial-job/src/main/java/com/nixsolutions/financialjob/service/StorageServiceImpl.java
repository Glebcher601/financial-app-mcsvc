package com.nixsolutions.financialjob.service;

import static com.nixsolutions.financial.discovery.ServiceRegistry.Services.STORAGE;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.nixsolutions.financial.discovery.ServiceRegistry;
import com.nixsolutions.financialjob.domain.StockSnapshotDto;

@Component
public class StorageServiceImpl implements StorageService
{
  private static final String STOCK_SNAPSHOTS_RESOURCE = "/stockSnapshots";

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Override
  public void save(List<StockSnapshotDto> snapshotDtos)
  {
    getWebClient().put()
        .body(BodyInserters.fromObject(snapshotDtos))
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
