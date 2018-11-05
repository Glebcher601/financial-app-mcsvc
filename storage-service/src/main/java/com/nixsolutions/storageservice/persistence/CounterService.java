package com.nixsolutions.storageservice.persistence;

public interface CounterService
{
  long nextValue(String name);
}
