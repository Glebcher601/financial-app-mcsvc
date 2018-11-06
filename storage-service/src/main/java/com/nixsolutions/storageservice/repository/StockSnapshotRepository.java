package com.nixsolutions.storageservice.repository;

import com.nixsolutions.storageservice.domain.StockSnapshot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockSnapshotRepository extends MongoRepository<StockSnapshot, String>,
    StockSnapshotRepositoryCustom
{
  Optional<StockSnapshot> findBySymbol(String s);

}
