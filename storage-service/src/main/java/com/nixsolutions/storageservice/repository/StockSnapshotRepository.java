package com.nixsolutions.storageservice.repository;

import com.nixsolutions.storageservice.domain.SymbolStockSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockSnapshotRepository extends MongoRepository<SymbolStockSnapshot, String>
{
  Optional<SymbolStockSnapshot> findBySymbol(String s);
}
