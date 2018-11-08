package com.nixsolutions.storageservice.persistence.repository;

import com.nixsolutions.storageservice.domain.StockSnapshot;
import com.nixsolutions.storageservice.domain.SymbolStockSnapshots;
import reactor.core.publisher.Flux;

public interface StockSnapshotRepositoryCustom extends SequnceGeneratorAware
{
  Flux<StockSnapshot> addSnapshots(SymbolStockSnapshots symbolStockSnapshots);
}
