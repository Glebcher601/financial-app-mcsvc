package com.nixsolutions.storageservice.repository;

import com.nixsolutions.storageservice.domain.SymbolStockSnapshots;

public interface StockSnapshotRepositoryCustom extends SequnceGeneratorAware
{
  void addSnapshots(SymbolStockSnapshots symbolStockSnapshots);
}
