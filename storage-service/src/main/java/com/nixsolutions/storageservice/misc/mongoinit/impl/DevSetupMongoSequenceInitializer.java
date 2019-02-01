package com.nixsolutions.storageservice.misc.mongoinit.impl;

import com.nixsolutions.storageservice.misc.mongoinit.MongoSequenceInitializer;
import com.nixsolutions.storageservice.persistence.MongoSequence;
import com.nixsolutions.storageservice.persistence.repository.SequnceGeneratorAware;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
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
        .filter(((Predicate<? super SequnceGeneratorAware>) AopUtils::isAopProxy).negate())
        .forEach(gen -> initSequence(gen.getSequenceName()));
  }

  private void initSequence(String name)
  {
    Query query = new Query().addCriteria(Criteria.where("name").is(name));
    Update update = new Update().min("value", 1);

    mongoTemplate.upsert(query, update, MongoSequence.class);
  }
}
