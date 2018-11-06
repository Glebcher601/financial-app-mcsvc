package com.nixsolutions.storageservice.persistence;

public interface DbSequenceService
{
  long nextValue(String name);
}
