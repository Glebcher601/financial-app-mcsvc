package com.nixsolutions.storageservice;

import com.nixsolutions.financial.domain.StockSnapshotBase;
import com.nixsolutions.storageservice.domain.SymbolStockSnapshot;
import com.nixsolutions.storageservice.persistence.IdGenService;
import com.nixsolutions.storageservice.repository.StockSnapshotRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageServiceApplicationTests
{

  @Autowired
  StockSnapshotRepository repository;

  @Autowired
  IdGenService idGenService;

  @Test
  public void contextLoads()
  {
    SymbolStockSnapshot stockSnapshots = new SymbolStockSnapshot();
    stockSnapshots.setSymbol("MSFT");

    StockSnapshotBase stockSnapshot = new StockSnapshotBase();
    stockSnapshot.setHigh(123);
    stockSnapshot.setLow(145);
    stockSnapshot.setOpen(111);
    stockSnapshot.setClose(112);
    stockSnapshot.setTimeStamp(new Date());

    stockSnapshots.setSnapshots(new HashSet<>(Lists.newArrayList(stockSnapshot)));

    //repository.save(stockSnapshots);

    Query query = new Query(Criteria.where("symbol").is("MSFT"));
    //Update update = new Update().addToSet()

  }

}
