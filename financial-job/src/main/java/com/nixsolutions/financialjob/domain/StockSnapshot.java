package com.nixsolutions.financialjob.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nixsolutions.financial.domain.StockSnapshotBase;

public class StockSnapshot extends StockSnapshotBase
{
  @Override
  @JsonProperty
  public Date getTimeStamp()
  {
    return super.getTimeStamp();
  }

  @Override
  @JsonProperty
  public double getClose()
  {
    return super.getClose();
  }

  @Override
  @JsonProperty
  public double getOpen()
  {
    return super.getOpen();
  }

  @Override
  @JsonProperty
  public int getVolume()
  {
    return super.getVolume();
  }

  @Override
  @JsonProperty
  public double getHigh()
  {
    return super.getHigh();
  }

  @Override
  @JsonProperty
  public double getLow()
  {
    return super.getLow();
  }
}
