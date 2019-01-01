package com.nixsolutions.financialjob.service;

import com.nixsolutions.financial.discovery.ServiceRegistry;
import com.nixsolutions.financial.discovery.ServiceRegistryAware;
import com.nixsolutions.financial.security.properties.SystemJwtAuthenticationHolder;
import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.Optional;

import static com.nixsolutions.financial.discovery.ServiceRegistryKt.STORAGE;

@Component
public class StorageServiceImpl implements StorageService, ServiceRegistryAware
{
  private static final String STOCK_SNAPSHOTS_RESOURCE = "stockSnapshots";

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Autowired
  private SystemJwtAuthenticationHolder jwtAuthenticationHolder;

  @Override
  public void save(SymbolStockSnapshots symbolStockSnapshots)
  {
    final Optional<ClientResponse> clientResponse = jwtAuthenticationHolder
        .getSystemWebClient(composePath(STOCK_SNAPSHOTS_RESOURCE))
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
