package com.nixsolutions.financialjob.service;

import com.nixsolutions.financial.domain.SymbolStockSnapshotsBase;

public interface DataPullService
{
  SymbolStockSnapshotsBase pullSnapshotsForSymbol(String symbol);
}
