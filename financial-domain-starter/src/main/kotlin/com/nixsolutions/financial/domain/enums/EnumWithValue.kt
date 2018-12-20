package com.nixsolutions.financial.domain.enums

interface EnumWithValue<E : Enum<E>, T> {

  val value: T

  fun getByValue(value: T): E

  fun iterateThroughValuesForMatch(value: T, values: Array<E>): E {
    for (enumWithValue in values) {
      if ((enumWithValue as EnumWithValue<*, *>).value == value) {
        return enumWithValue
      }
    }
    throw IllegalStateException(value.toString() + " is not present in enum" + values.javaClass.componentType)
  }
}
