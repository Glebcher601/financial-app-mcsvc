package com.nixsolutions.storageservice.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class IdGenService implements CounterService
{
  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public long nextValue(String counterName)
  {
    return increaseSequence(counterName);
  }

  public long increaseSequence(String counterName)
  {
    Query query = new Query(Criteria.where("name").is(counterName));
    Update update = new Update().inc("sequence", 1);
    MongoCounter counter = mongoTemplate.findAndModify(query, update, MongoCounter.class); // return old Counter object

    return counter.getValue();
  }
}
