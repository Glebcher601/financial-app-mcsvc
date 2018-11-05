package com.nixsolutions.storageservice.persistence;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document("sequence")
public class MongoIdSequence
{
  private String name;

  private long value;
}
