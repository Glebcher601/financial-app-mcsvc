package com.nixsolutions.financial.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SymbolStockSnapshotsBase extends BaseEntity
{
  private String symbol;

  private Set<StockSnapshotBase> snapshots;
}
