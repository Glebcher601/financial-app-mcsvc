package com.nixsolutions.financial.discovery;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceRegistryTest
{
  private static final String SVC_NAME_LOCAL = "service_local";
  private static final String SVC_NAME_CLUSTER = "service_cluster";
  private static final String NON_EXIST_SVC_NAME = "service_non_exist";

  private ObjectMapper objectMapper = new ObjectMapper();
  private ServiceDiscoveryProperties svcDiscProps = getSvcDiscoveryProps("serviceRecords.json");
  private ServiceRegistry serviceRegistry;

  @BeforeEach
  public void before()
  {
    serviceRegistry = new ServiceRegistry();
    serviceRegistry.setServiceDiscoveryProperties(svcDiscProps);
  }

  @ParameterizedTest(name = "Should return {0}")
  @MethodSource("argumentsForShouldReturnCorrectUrl")
  public void shouldReturnCorrectUrl(String name, String svcName,
                                     String expectedUrl) throws Exception
  {
    //when
    String actualUrl = serviceRegistry.getServiceUrl(svcName);

    //then
    assertEquals(expectedUrl, actualUrl);
  }

  private Stream<Arguments> argumentsForShouldReturnCorrectUrl()
  {
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
    );
  }

  @Test
  public void shouldFailOnNonExistngSvc()
  {
    //when
    Executable executable = () -> serviceRegistry.getServiceUrl(NON_EXIST_SVC_NAME);

    //then
    Assertions.assertThrows(DiscoveryException.class, executable);
  }

  private ServiceDiscoveryProperties getSvcDiscoveryProps(String file)
  {
    try
    {
      ServiceRecord[] serviceRecords = objectMapper.readValue(getClass().getResourceAsStream(file),
          ServiceRecord[].class);
      ServiceDiscoveryProperties serviceDiscoveryProperties = new ServiceDiscoveryProperties();
      serviceDiscoveryProperties.setList(Arrays.asList(serviceRecords));
      return serviceDiscoveryProperties;
    }
    catch (IOException e)
    {
      throw new RuntimeException();
    }
  }
}