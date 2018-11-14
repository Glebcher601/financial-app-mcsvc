package com.nixsolutions.financialjob.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.nixsolutions.financial.config.EnableSimpleDiscovery;
import com.nixsolutions.financial.discovery.ServiceRegistry;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StorageServiceImplTest.class, initializers = ConfigFileApplicationContextInitializer
    .class)
@EnableSimpleDiscovery
@Configuration
public class StorageServiceImplTest
{
  private static final String TEST_SVC = "TEST";

  @Rule
  public MockWebServer mockWebServer = new MockWebServer();

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Before
  public void setUp() throws Exception
  {
    serviceRegistry.getServiceDiscoveryProperties().getList()
        .stream()
        .filter(svcRecord -> StringUtils.equals(svcRecord.getName(), TEST_SVC))
        .forEach(svcRecord -> svcRecord.setPort(String.valueOf(mockWebServer.getPort())));

  }

  @Test
  public void shouldThrowExceptionIfErrorResultRecieved() throws Exception
  {
    //given
    mockWebServer.enqueue(new MockResponse().setStatus("500"));

    //when


    //then
  }

  @Test
  public void shouldSucceedOnOkResult() throws Exception
  {
  }


}