package com.nixsolutions.financial.domain.enums;

public enum Endpoints implements EnumWithValue<Endpoints, String>
{
  TIME_SERIES_INTRADAY("TIME_SERIES_INTRADAY"),
  TIME_SERIES_DAILY("TIME_SERIES_DAILY"),
  TIME_SERIES_DAILY_ADJUSTED("TIME_SERIES_DAILY_ADJUSTED"),
  TIME_SERIES_WEEKLY("TIME_SERIES_WEEKLY"),
  TIME_SERIES_MONTHLY("TIME_SERIES_MONTHLY");

  private String value;

  Endpoints(String value)
  {
    this.value = value;
  }

  public String getValue()
  {
    return value;
  }

  @Override
  public Endpoints getByValue(String value)
  {
    return iterateThroughValuesForMatch(value, values());
  }
}
