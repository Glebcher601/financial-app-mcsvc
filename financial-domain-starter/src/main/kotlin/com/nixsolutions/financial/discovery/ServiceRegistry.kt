package com.nixsolutions.financial.discovery

import com.nixsolutions.financial.discovery.DiscoveryType.LOCAL

const val STORAGE = "storage-service"
const val UI = "ui"
const val PREDICTION = "prediction"
const val USER_AUTH = "authorization-server"
const val USERS = "user-service"

class ServiceRegistry {
  var serviceDiscoveryProperties: ServiceDiscoveryProperties? = null

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

  private val COLON = ":"
    private val HTTP_SCHEME = "http://"
    private val LOCALHOST = "localhost"
}
