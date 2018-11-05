package com.nixsolutions.storageservice.persistence;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("mongo_counter")
public class MongoCounter
{
  private String name;

  private long value;
}
