package com.nixsolutions.financial.discovery

import com.nixsolutions.financial.discovery.DiscoveryType.LOCAL

class ServiceRegistry {
  var serviceDiscoveryProperties: ServiceDiscoveryProperties? = null

  object Services {
    val STORAGE = "storage"
    val UI = "ui"
    val PREDICTION = "prediction"
    val USER_AUTH = "userAuth"
    val USERS = "user-service"
  }

  fun getServiceUrl(svcName: String): String {
    return this.serviceDiscoveryProperties!!.list.stream()
        .filter { svc -> svc.name == svcName }
        .findAny()
        .map { serviceRecordToUrl(it) }
        .orElseThrow<DiscoveryException> { DiscoveryException() }
  }

  private fun serviceRecordToUrl(serviceRecord: ServiceRecord): String {
    val resolution = serviceRecord.resolution
    val port = serviceRecord.port

    return if (resolution == LOCAL) {
      HTTP_SCHEME + LOCALHOST + COLON + port
    } else {
      HTTP_SCHEME + serviceRecord.name + COLON + port
    }
  }

  companion object {

    val COLON = ":"

    private val HTTP_SCHEME = "http://"
    private val LOCALHOST = "localhost"
  }
}
