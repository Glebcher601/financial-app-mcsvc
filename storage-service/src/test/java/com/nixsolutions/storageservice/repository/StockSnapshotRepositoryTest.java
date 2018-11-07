package com.nixsolutions.storageservice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = StockSnapshotRepositoryTest.class)
@ComponentScan("com.nixsolutions")
public class StockSnapshotRepositoryTest
{
  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  StockSnapshotRepository stockSnapshotRepository;

  @Test
  public void testSaveSymbolSnapshots() throws Exception
  {

  }
}