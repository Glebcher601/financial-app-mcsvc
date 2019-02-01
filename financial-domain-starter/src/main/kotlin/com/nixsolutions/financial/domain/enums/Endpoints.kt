package com.nixsolutions.financial.domain.enums

enum class Endpoints private constructor(override val value: String) : EnumWithValue<Endpoints, String> {
  TIME_SERIES_INTRADAY("TIME_SERIES_INTRADAY"),
  TIME_SERIES_DAILY("TIME_SERIES_DAILY"),
  TIME_SERIES_DAILY_ADJUSTED("TIME_SERIES_DAILY_ADJUSTED"),
  TIME_SERIES_WEEKLY("TIME_SERIES_WEEKLY"),
  TIME_SERIES_MONTHLY("TIME_SERIES_MONTHLY");

  override fun getByValue(value: String): Endpoints {
    return iterateThroughValuesForMatch(value, values())
  }
}
