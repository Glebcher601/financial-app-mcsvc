package com.nixsolutions.financial.discovery

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.IOException
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ServiceRegistryTest {

  private val objectMapper = ObjectMapper().registerModule(KotlinModule())
  private val svcDiscProps = getSvcDiscoveryProps("serviceRecords.json")
  private lateinit var serviceRegistry: ServiceRegistry

  @BeforeEach
  fun before() {
    serviceRegistry = ServiceRegistry()
    serviceRegistry.serviceDiscoveryProperties = svcDiscProps
  }

  @ParameterizedTest(name = "Should return {0}")
  @MethodSource("argumentsForShouldReturnCorrectUrl")
  @Throws(Exception::class)
  fun shouldReturnCorrectUrl(name: String, svcName: String,
                             expectedUrl: String) {
    //when
    val actualUrl = serviceRegistry.getServiceUrl(svcName)

    //then
    assertEquals(expectedUrl, actualUrl)
  }

  private fun argumentsForShouldReturnCorrectUrl(): Stream<Arguments> {
    return Stream.of(
        Arguments.of(
            "correct url of service name",
            SVC_NAME_LOCAL,
            "http://localhost:8888"
        ),
        Arguments.of(
            "correct url of cluster service",
            SVC_NAME_CLUSTER,
            "http://service_cluster:8888"
        )
    )
  }

  @Test
  fun shouldFailOnNonExistngSvc() {
    //when
    val executable = Executable { serviceRegistry.getServiceUrl(NON_EXIST_SVC_NAME) }

    //then
    Assertions.assertThrows(DiscoveryException::class.java, executable)
  }

  private fun getSvcDiscoveryProps(file: String): ServiceDiscoveryProperties {
    try {
      val serviceRecords = objectMapper.readValue<List<ServiceRecord>>(this::class.java.getResourceAsStream(file))
      val serviceDiscoveryProperties = ServiceDiscoveryProperties()
      serviceDiscoveryProperties.list = serviceRecords
      return serviceDiscoveryProperties
    } catch (e: IOException) {
      throw RuntimeException()
    }

  }

  companion object {
    private val SVC_NAME_LOCAL = "service_local"
    private val SVC_NAME_CLUSTER = "service_cluster"
    private val NON_EXIST_SVC_NAME = "service_non_exist"
  }
}