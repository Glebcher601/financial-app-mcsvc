package com.nixsolutions.financial.domain.enums

enum class Intervals private constructor(override val value: String) : EnumWithValue<Intervals, String> {
  ONE_MINUTE("1min"),
  FIVE_MINUTES("5min"),
  FIFTEEN_MINUTES("15min"),
  THIRTY_MINUTES("30min"),
  SIXTY_MINUTES("60min");

  override fun getByValue(value: String): Intervals {
    return iterateThroughValuesForMatch(value, values())
  }
}