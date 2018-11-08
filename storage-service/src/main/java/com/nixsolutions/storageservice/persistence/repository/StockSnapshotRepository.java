package com.nixsolutions.storageservice.persistence.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.nixsolutions.storageservice.domain.StockSnapshot;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StockSnapshotRepository extends ReactiveMongoRepository<StockSnapshot, String>,
    StockSnapshotRepositoryCustom
{
  Flux<StockSnapshot> findBySymbol(String s);

  Mono<Void> deleteBySymbol(String symbol);

}
