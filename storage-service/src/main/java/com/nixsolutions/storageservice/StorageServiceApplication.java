package com.nixsolutions.storageservice;

import com.nixsolutions.financial.security.config.EnableJwtProtection;
import com.nixsolutions.storageservice.misc.mongoinit.MongoSequenceInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Objects;

@EnableMongoAuditing
@SpringBootApplication
@EnableJwtProtection(useDefault = false)
public class StorageServiceApplication implements ApplicationListener<ApplicationReadyEvent>
{
  @Autowired(required = false)
  private MongoSequenceInitializer mongoSequenceInitializer;

  public static void main(String[] args)
  {
    SpringApplication.run(StorageServiceApplication.class, args);
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
  {
    if (Objects.nonNull(mongoSequenceInitializer))
    {
      mongoSequenceInitializer.init();
    }
  }
}
