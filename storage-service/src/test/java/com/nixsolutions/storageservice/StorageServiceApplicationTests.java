package com.nixsolutions.storageservice;

import com.nixsolutions.storageservice.persistence.IdGenService;
import com.nixsolutions.storageservice.persistence.repository.StockSnapshotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

  }

}
