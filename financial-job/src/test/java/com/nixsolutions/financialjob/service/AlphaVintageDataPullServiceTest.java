package com.nixsolutions.financialjob.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(JUnit4ClassRunner.class)
public class AlphaVintageDataPullServiceTest
{
  @Autowired
  DataPullService dataPullService;

  @Rule
  public MockWebServer mockWebServer = new MockWebServer();

  @Before
  public void setUp() throws Exception
  {
    String body = IOUtils.toString(AlphaVintageDataPullService.class.getResourceAsStream
        ("response/alpha-vintage-response.json"), "UTF-8");

    mockWebServer.enqueue(new MockResponse()
        .setBody(body)
        .setStatus("HTTP/1.1 200 OK")
        .addHeader("Content-type", "application/json"));
  }

  @Test
  public void testOverall() throws Exception
  {
    SymbolStockSnapshots symbolStockSnapshotsActual = new AlphaVintageDataPullService(mockWebServer.url("").toString())
        .pullSnapshotsForSymbol("MSFT");

    SymbolStockSnapshots symbolStockSnapshotsExpected = new ObjectMapper().readValue(AlphaVintageDataPullService.class
        .getResourceAsStream("deserialized/deserialized-response.json"), SymbolStockSnapshots.class);

    Assert.assertEquals(symbolStockSnapshotsExpected, symbolStockSnapshotsActual);
  }

}