package com.nixsolutions.storageservice.resource;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nixsolutions.storageservice.domain.StockSnapshot;
import com.nixsolutions.storageservice.domain.SymbolStockSnapshots;
import com.nixsolutions.storageservice.persistence.repository.StockSnapshotRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/stocksSnapshots")
@RestController
public class StockSnapshotResource
{
  private final StockSnapshotRepository stockSnapshotRepository;

  @Autowired
  public StockSnapshotResource(StockSnapshotRepository stockSnapshotRepository)
  {
    this.stockSnapshotRepository = stockSnapshotRepository;
  }

  @GetMapping
  public Flux<StockSnapshot> findAll()
  {
    return stockSnapshotRepository.findAll();
  }

  @GetMapping("/{symbol}")
  public Mono<ResponseEntity<List<StockSnapshot>>> findBySymbol(@PathVariable String symbol)
  {
    return stockSnapshotRepository.findBySymbol(symbol)
        .collectList()
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ResponseEntity<List<StockSnapshot>>> createSnapshots(@RequestBody SymbolStockSnapshots
                                                                       symbolStockSnapshots)
  {
    return stockSnapshotRepository.addSnapshots(symbolStockSnapshots).collectList()
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{symbol}")
  public Mono<ResponseEntity<Void>> deleteBySymbol(@PathVariable String symbol)
  {
    return stockSnapshotRepository.findBySymbol(symbol)
        .flatMap(existingSnapshot -> stockSnapshotRepository.delete(existingSnapshot)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
        .last();
  }

}
