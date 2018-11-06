package com.nixsolutions.financialjob.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockSnapshot
{
  private String symbol;

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
