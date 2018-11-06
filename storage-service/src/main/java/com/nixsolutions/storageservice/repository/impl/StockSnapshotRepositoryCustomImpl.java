package com.nixsolutions.storageservice.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.nixsolutions.storageservice.domain.SymbolStockSnapshots;
import com.nixsolutions.storageservice.persistence.DbSequenceService;
import com.nixsolutions.storageservice.repository.StockSnapshotRepositoryCustom;

public class StockSnapshotRepositoryCustomImpl implements StockSnapshotRepositoryCustom
{
  private static final String SEQ_NAME = "seq_stock_snapshot";

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private DbSequenceService dbSequenceService;

  @Override
  public void addSnapshots(SymbolStockSnapshots symbolStockSnapshots)
  {
    symbolStockSnapshots.getSnapshots()
        .forEach(snapshot -> snapshot.setSymbol(symbolStockSnapshots.getSymbol()));

    symbolStockSnapshots.getSnapshots()
        .forEach(snapshot -> snapshot.setId(dbSequenceService.nextValue(getSequenceName())));

    mongoTemplate.insertAll(symbolStockSnapshots.getSnapshots());
  }

  @Override
  public String getSequenceName()
  {
    return SEQ_NAME;
  }
}
