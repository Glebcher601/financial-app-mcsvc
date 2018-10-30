package com.nixsolutions.financialjob.service;

import java.util.List;
import com.nixsolutions.financial.domain.StockSnapshot;

public interface DataPullService
{
  List<StockSnapshot> pullSnapshotsForSymbol(String symbol);
}
