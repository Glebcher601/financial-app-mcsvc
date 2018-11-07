package com.nixsolutions.storageservice.persistence;

import reactor.core.publisher.Mono;

public interface DbSequenceService
{
  Mono<Long> nextValue(String name);
}
