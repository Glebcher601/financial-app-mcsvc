package com.nixsolutions.storageservice.repository;

import com.nixsolutions.financial.domain.StockSnapshotBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockSnapshotRepositoryCustomImpl implements StockSnapshotRepositoryCustom
{

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public void addSnapshots(String symbol, List<StockSnapshotBase> snapshots)
  {
    Query query = new Query(Criteria.where("symbol").is(symbol));
    Update update = new Update().addToSet("snapshots").each(snapshots.toArray());

    //mongoTemplate.findAndModify(query, update, SymbolStockSnapshot.class);

  }
}
