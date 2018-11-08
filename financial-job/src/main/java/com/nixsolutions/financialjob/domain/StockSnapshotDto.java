package com.nixsolutions.financialjob.domain;

import java.util.Date;
import lombok.Data;

@Data
public class StockSnapshotDto
{
  private Date timeStamp;

  private double open;

  private double low;

  private double high;

  private double close;

  private int volume;

  public StockSnapshotDto(StockSnapshotAlphaVantage stockSnapshot)
  {
    this.high = stockSnapshot.getHigh();
    this.low = stockSnapshot.getLow();
    this.close = stockSnapshot.getClose();
    this.open = stockSnapshot.getOpen();
    this.volume = stockSnapshot.getVolume();
    this.timeStamp = stockSnapshot.getTimeStamp();
  }

}
