package com.nixsolutions.financialjob.service;

import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;

public interface DataPullService
{
  SymbolStockSnapshots pullSnapshotsForSymbol(String symbol);
}
