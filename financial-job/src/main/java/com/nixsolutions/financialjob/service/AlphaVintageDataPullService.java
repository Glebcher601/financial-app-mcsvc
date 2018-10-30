package com.nixsolutions.financialjob.service;

import static com.nixsolutions.financialjob.configuration.ApiUrlConfiguration.UriParams.SYMBOL;
import static java.util.Spliterator.ORDERED;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterators;
import java.util.TimeZone;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nixsolutions.financial.domain.StockSnapshot;
import com.nixsolutions.financialjob.configuration.ApiUrlConfiguration;

@Service
public class AlphaVintageDataPullService implements DataPullService
{
  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  @Qualifier("prefilledTemplateUrl")
  private String templateUri;

  @Override
  public List<StockSnapshot> pullSnapshotsForSymbol(String symbol)
  {
    String uriString = UriComponentsBuilder.fromHttpUrl(templateUri)
        .buildAndExpand(symbol)
        .toUriString();

    WebClient.create(uriString).method(HttpMethod.GET)
        .retrieve()
        .bodyToMono(JsonNode.class)
        .map(this::getStockSnapShots)
        .map(this::toEntityList)
        .block();

    return null;
  }

  private ObjectNode getStockSnapShots(JsonNode jsonNode)
  {
    Optional<Map.Entry<String, JsonNode>> nodeEntry = StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode
        .fields(), ORDERED), false)
        .reduce((first, second) -> second);

    return nodeEntry
        .map(Map.Entry::getValue)
        .map(node -> (ObjectNode) node)
        .orElseThrow(RuntimeException::new);
  }

  private List<StockSnapshot> toEntityList(ObjectNode objectNode)
  {
    ArrayList<StockSnapshot> result = new ArrayList<>();
    objectNode.fields()
        .forEachRemaining(entry -> result.add(createStockSnapshot(entry.getKey(), entry.getValue())));

    return result;
  }

  private StockSnapshot createStockSnapshot(String timeStamp, JsonNode stockData)
  {
    StockSnapshot stockSnapshot;
    try
    {
      stockSnapshot = objectMapper.treeToValue(stockData, StockSnapshot.class);
      stockSnapshot.setTimeStamp(format.parse(timeStamp));
      return stockSnapshot;
    }
    catch (JsonProcessingException | ParseException e)
    {
      throw new RuntimeException(e);
    }
  }

  private StockSnapshot toPojo(JsonNode jsonNode)
  {
    return objectMapper.convertValue(jsonNode, StockSnapshot.class);
  }
}