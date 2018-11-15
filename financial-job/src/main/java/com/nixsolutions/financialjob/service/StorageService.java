package com.nixsolutions.financialjob.service;

import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;

public interface StorageService
{
  void save(SymbolStockSnapshots symbolStockSnapshots);
}
