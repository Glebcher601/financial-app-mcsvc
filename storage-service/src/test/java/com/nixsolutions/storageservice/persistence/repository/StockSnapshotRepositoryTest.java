package com.nixsolutions.storageservice.persistence.repository;

import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.storageservice.domain.StockSnapshot;
import com.nixsolutions.storageservice.domain.SymbolStockSnapshots;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ContextConfiguration(classes = StockSnapshotRepositoryTest.class)
@ComponentScan("com.nixsolutions")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StockSnapshotRepositoryTest
{
  private ObjectMapper objectMapper = new ObjectMapper();
  private static final String TEST_DATA_FOLDER = "test-data";
  private static final String ACTUAL_DATA_FOLDER = "actual-data";

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private StockSnapshotRepository stockSnapshotRepository;

  @Test
  public void testSaveSymbolSnapshots() throws Exception
  {
    //when
    stockSnapshotRepository
        .addSnapshots(getTestingData(TEST_DATA_FOLDER + "/symbolstockcnapshots.json", SymbolStockSnapshots.class))
        .collectList().block();

    //then
    Flux<StockSnapshot> snapshotsActual = stockSnapshotRepository.findAll();

    StepVerifier.create(snapshotsActual)
        .expectNext(getTestingData(ACTUAL_DATA_FOLDER + "/symbolStockSnapshots-expected.json", StockSnapshot[].class))
        .expectComplete()
        .verify();
  }

  @Test
  public void testFindBySymbol() throws Exception
  {
    //given
    stockSnapshotRepository.saveAll(
        Arrays.asList(getTestingData(TEST_DATA_FOLDER + "/snapshotsForDiffSymbols.json", StockSnapshot[].class)))
        .collectList().block();

    //when
    Flux<StockSnapshot> msft = stockSnapshotRepository.findBySymbol("MSFT");

    //then
    StepVerifier.create(msft)
        .expectNext(getTestingData(ACTUAL_DATA_FOLDER + "/diffSymbolsMSFT-expected.json", StockSnapshot.class))
        .expectComplete()
        .verify();
  }

  @AfterEach
  public void afterTest()
  {
    
  }

  private <E> E getTestingData(String file, Class<E> clazz) throws IOException
  {
    return objectMapper.readValue(this.getClass().getResourceAsStream(file), clazz);
  }
}