package com.nixsolutions.financial.domain.enums;

public enum Intervals implements EnumWithValue<Intervals, String>
{
  ONE_MINUTE("1min"),
  FIVE_MINUTES("5min"),
  FIFTEEN_MINUTES("15min"),
  THIRTY_MINUTES("30min"),
  SIXTY_MINUTES("60min");

  private String value;

  Intervals(String value)
  {
    this.value = value;
  }

  public String getValue()
  {
    return value;
  }

  @Override
  public Intervals getByValue(String value)
  {
    return iterateThroughValuesForMatch(value, values());
  }
}