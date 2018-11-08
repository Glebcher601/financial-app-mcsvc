package com.nixsolutions.storageservice.persistence.repository.impl;

import static java.util.stream.Collectors.toList;
import java.util.Collection;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import com.nixsolutions.storageservice.domain.StockSnapshot;
import com.nixsolutions.storageservice.domain.SymbolStockSnapshots;
import com.nixsolutions.storageservice.persistence.DbSequenceService;
import com.nixsolutions.storageservice.persistence.repository.StockSnapshotRepositoryCustom;
import reactor.core.publisher.Flux;

public class StockSnapshotRepositoryCustomImpl implements StockSnapshotRepositoryCustom
{
  private static final String SEQ_NAME = "seq_stock_snapshot";

  @Autowired
  private ReactiveMongoTemplate mongoTemplate;

  @Autowired
  private DbSequenceService dbSequenceService;

  @Override
  public Flux<StockSnapshot> addSnapshots(SymbolStockSnapshots symbolStockSnapshots)
  {
    prefillWithSymbol(symbolStockSnapshots.getSnapshots(), symbolStockSnapshots.getSymbol());

    int itemsAddedLength = symbolStockSnapshots.getSnapshots().size();
    Flux<Long> ids = Flux.mergeSequential(
        Stream.generate(() -> dbSequenceService.nextValue(getSequenceName()))
            .limit(itemsAddedLength)
            .collect(toList()));
    Flux<StockSnapshot> stockSnapshots = Flux.fromIterable(symbolStockSnapshots.getSnapshots());

    Flux<StockSnapshot> readyToPersistSnapshots = Flux.zip(stockSnapshots, ids, this::fillWithId);

    return mongoTemplate.insertAll(readyToPersistSnapshots.collectList());
  }

  private void prefillWithSymbol(Collection<StockSnapshot> snapshots, String symbol)
  {
    snapshots.forEach(snapshot -> snapshot.setSymbol(symbol));
  }

  private StockSnapshot fillWithId(StockSnapshot snapshot, Long id)
  {
    snapshot.setId(id);
    return snapshot;
  }

  @Override
  public String getSequenceName()
  {
    return SEQ_NAME;
  }
}
