package com.nixsolutions.financialjob.service;

import static com.nixsolutions.financial.discovery.ServiceRegistryKt.STORAGE;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import com.nixsolutions.financial.discovery.ServiceRegistry;
import com.nixsolutions.financial.discovery.ServiceRegistryAware;
import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;

@Component
public class StorageServiceImpl implements StorageService, ServiceRegistryAware
{
  private static final String STOCK_SNAPSHOTS_RESOURCE = "stockSnapshots";

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Override
  public void save(SymbolStockSnapshots symbolStockSnapshots)
  {
/*    getWebClient(composePath(STOCK_SNAPSHOTS_RESOURCE))
        .post()
        .body(BodyInserters.fromObject(symbolStockSnapshots))
        .exchange()
        .map(ClientResponse::statusCode)
        .blockOptional()
        .filter(HttpStatus::is2xxSuccessful)
        .orElseThrow(RuntimeException::new);*/

    final Optional<ClientResponse> clientResponse = getWebClient(composePath(STOCK_SNAPSHOTS_RESOURCE))
        .post()
        .body(BodyInserters.fromObject(symbolStockSnapshots))
        .exchange()
        .blockOptional();
  }

  @Override
  public String getServiceUrl()
  {
    return serviceRegistry.getServiceUrl(STORAGE);
  }
}
