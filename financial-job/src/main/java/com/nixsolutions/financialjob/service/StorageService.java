package com.nixsolutions.financialjob.service;

import java.util.List;
import com.nixsolutions.financialjob.domain.StockSnapshotDto;

public interface StorageService
{
  void save(List<StockSnapshotDto> snapshotDtos);
}
