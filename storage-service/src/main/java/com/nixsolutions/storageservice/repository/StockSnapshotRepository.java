package com.nixsolutions.storageservice.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.nixsolutions.storageservice.domain.StockSnapshot;

@Repository
public interface StockSnapshotRepository extends MongoRepository<StockSnapshot, String>, StockSnapshotRepositoryCustom
{
  Optional<StockSnapshot> findBySymbol(String s);

}
