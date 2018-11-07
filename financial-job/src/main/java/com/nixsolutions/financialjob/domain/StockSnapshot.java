package com.nixsolutions.financialjob.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockSnapshot
{
  private Date timeStamp;

  private double open;

  private double low;

  private double high;

  private double close;

  private int volume;

  public void setTimeStamp(Date timeStamp)
  {
    this.timeStamp = timeStamp;
  }

  @JsonProperty("1. open")
  public void setOpen(double open)
  {
    this.open = open;
  }

  @JsonProperty("3. low")
  public void setLow(double low)
  {
    this.low = low;
  }

  @JsonProperty("2. high")
  public void setHigh(double high)
  {
    this.high = high;
  }

  @JsonProperty("4. close")
  public void setClose(double close)
  {
    this.close = close;
  }

  @JsonProperty("5. volume")
  public void setVolume(int volume)
  {
    this.volume = volume;
  }
}
