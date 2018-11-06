package com.nixsolutions.storageservice.domain;

import java.util.Set;
import lombok.Data;

@Data
public class SymbolStockSnapshots
{
  private String symbol;

  private Set<StockSnapshot> snapshots;
}