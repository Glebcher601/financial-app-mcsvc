package com.nixsolutions.financialjob.service;

import com.nixsolutions.financial.domain.SymbolStockSnapshots;

public interface DataPullService
{
  SymbolStockSnapshots pullSnapshotsForSymbol(String symbol);
}
