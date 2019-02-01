package com.nixsolutions.financial.discovery

class DiscoveryException : RuntimeException {
  constructor() : super("Service not found") {}

  constructor(message: String) : super(message) {}
}
