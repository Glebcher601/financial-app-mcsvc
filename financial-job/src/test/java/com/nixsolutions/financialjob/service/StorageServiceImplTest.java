package com.nixsolutions.financialjob.service;

import com.nixsolutions.financial.config.EnableSimpleDiscovery;
import com.nixsolutions.financial.discovery.ServiceRegistry;
import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = StorageServiceImplTest.class, initializers = ConfigFileApplicationContextInitializer
    .class)
@EnableSimpleDiscovery
@ComponentScan(basePackages = "com.nixsolutions.financialjob.service",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AlphaVintageDataPullService
        .class))
@Configuration
public class StorageServiceImplTest
{
  @Rule
  public MockWebServer mockWebServer = new MockWebServer();

  @Autowired
  private ServiceRegistry serviceRegistry;

  @Autowired
  private StorageService storageService;

  @Before
  public void setUp() throws Exception
  {
    serviceRegistry.getServiceDiscoveryProperties().getList()
        .stream()
        .filter(svcRecord -> StringUtils.equals(svcRecord.getName(), ServiceRegistry.Services.STORAGE))
        .forEach(svcRecord -> svcRecord.setPort(String.valueOf(mockWebServer.getPort())));

  }

  @Test
  public void shouldThrowExceptionIfErrorResultRecieved()
  {
    //given
    mockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 500 INTERNAL SERVER ERROR"));

    //when
    try
    {
      storageService.save(new SymbolStockSnapshots());
      Assertions.fail("Exception expected| None present");
    } catch (RuntimeException e)
    {

    }

    //then
  }

  @Test
  public void shouldSucceedOnOkResult() throws Exception
  {
    //given
    mockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 200 OK"));

    //when
    storageService.save(new SymbolStockSnapshots());
  }


}