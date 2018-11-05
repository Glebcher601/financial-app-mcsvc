package com.nixsolutions.storageservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nixsolutions.storageservice.domain.SymbolStockSnapshot;

@Repository
public interface StockSnapshotRepository extends MongoRepository<SymbolStockSnapshot, String>,
    StockSnapshotRepositoryCustom
{
  Optional<SymbolStockSnapshot> findBySymbol(String s);

}
