package com.nixsolutions.storageservice.repository;

import java.util.List;
import com.nixsolutions.financial.domain.StockSnapshotBase;

public interface StockSnapshotRepositoryCustom
{
  void addSnapshots(String symbol, List<StockSnapshotBase> snapshots);
}
