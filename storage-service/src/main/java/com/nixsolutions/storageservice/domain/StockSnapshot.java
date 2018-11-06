package com.nixsolutions.storageservice.domain;

import java.util.Date;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "stockSnapshot")
public class StockSnapshot
{
  @Indexed
  private long id;

  private String symbol;

  private Date timeStamp;

  private double open;

  private double low;

  private double high;

  private double close;

  private int volume;
}
