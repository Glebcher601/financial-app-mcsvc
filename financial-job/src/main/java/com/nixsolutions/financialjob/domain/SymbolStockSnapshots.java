package com.nixsolutions.financialjob.domain;

import java.util.Set;
import lombok.Data;

@Data
public class SymbolStockSnapshots
{
  private String symbol;

  private Set<StockSnapshotAlphaVantage> snapshots;
}