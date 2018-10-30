package com.nixsolutions.financial.domain;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SymbolStockSnapshots extends BaseEntity
{
  private String symbol;

  private List<StockSnapshot> snapshots;
}
