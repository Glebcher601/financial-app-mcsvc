package com.nixsolutions.storageservice.misc.mongoinit.impl;

import java.util.List;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import com.nixsolutions.storageservice.misc.mongoinit.MongoSequenceInitializer;
import com.nixsolutions.storageservice.persistence.MongoSequence;
import com.nixsolutions.storageservice.repository.SequnceGeneratorAware;

@Component
@Profile("dev")
public class DevSetupMongoSequenceInitializer implements MongoSequenceInitializer
{
  private final MongoTemplate mongoTemplate;

  @Autowired
  private List<SequnceGeneratorAware> generatorAwares;

  @Autowired
  public DevSetupMongoSequenceInitializer(MongoTemplate mongoTemplate)
  {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void init()
  {
    generatorAwares.stream()
        .filter(AopUtils::isAopProxy)
        .forEach(gen -> initSequence(gen.getSequenceName()));
  }

  private void initSequence(String name)
  {
    Query query = new Query().addCriteria(Criteria.where("name").is(name));
    Update update = new Update().inc("value", 1);

    mongoTemplate.upsert(query, update, MongoSequence.class);
  }
}
