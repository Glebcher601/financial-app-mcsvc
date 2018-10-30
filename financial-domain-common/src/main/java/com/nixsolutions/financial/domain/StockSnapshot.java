package com.nixsolutions.financial.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "stock_snapshot")
@Data
public class StockSnapshot implements Serializable {
  @Id
  private long id;

  private Date timeStamp;

  @JsonProperty("1. open")
  private double open;

  @JsonProperty("2. high")
  private double low;

  @JsonProperty("3. low")
  private double high;

  @JsonProperty("4. close")
  private double close;

  @JsonProperty("5. volume")
  private int volume;
}
