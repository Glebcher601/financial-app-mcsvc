package com.nixsolutions.financialjob.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nixsolutions.financialjob.domain.StockSnapshotAlphaVantage;
import com.nixsolutions.financialjob.domain.SymbolStockSnapshots;
import com.nixsolutions.financialjob.misc.ThrowingFunction;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterators;
import java.util.TimeZone;
import java.util.stream.StreamSupport;

import static com.nixsolutions.financialjob.misc.ThrowingFunction.uncheckedFn;
import static java.util.Spliterator.ORDERED;

@Service
public class AlphaVintageDataPullService implements DataPullService
{
  private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
  public static final String TIME_ZONE_FIELD = "6. Time Zone";
  public static final String META_DATA_FIELD = "Meta Data";
  private ObjectMapper objectMapper = new ObjectMapper();

  private final String templateUri;

  @Autowired
  public AlphaVintageDataPullService(@Qualifier("prefilledTemplateUrl") String templateUri)
  {
    this.templateUri = templateUri;
  }

  @Override
  @Timed(value = "pullDuration")
  public SymbolStockSnapshots pullSnapshotsForSymbol(String symbol)
  {
    String uriString = UriComponentsBuilder.fromHttpUrl(templateUri)
        .buildAndExpand(symbol)
        .toUriString();

    List<StockSnapshotAlphaVantage> stockSnapshots = WebClient.create(uriString).method(HttpMethod.GET)
        .retrieve()
        .bodyToMono(JsonNode.class)
        .map(this::getStockSnapShots)
        .block();

    SymbolStockSnapshots symbolStockSnapshots = new SymbolStockSnapshots();
    symbolStockSnapshots.setSymbol(symbol);
    symbolStockSnapshots.setSnapshots(new HashSet<>(stockSnapshots));

    return symbolStockSnapshots;
  }

  private List<StockSnapshotAlphaVantage> getStockSnapShots(JsonNode jsonNode)
  {
    Optional<Map.Entry<String, JsonNode>> snapshotsNode =
        StreamSupport.stream(Spliterators.spliteratorUnknownSize(jsonNode.fields(), ORDERED), false)
            .reduce((first, second) -> second);

    String timeZone = jsonNode.get(META_DATA_FIELD).get(TIME_ZONE_FIELD).asText();

    return snapshotsNode
        .map(Map.Entry::getValue)
        .map(node -> (ObjectNode) node)
        .map(node -> this.toEntityList(node, timeZone))
        .orElseThrow(RuntimeException::new);
  }

  private List<StockSnapshotAlphaVantage> toEntityList(ObjectNode objectNode, String timeZone)
  {
    ArrayList<StockSnapshotAlphaVantage> result = new ArrayList<>();

    objectNode.fields()
        .forEachRemaining(entry ->
            result.add(createStockSnapshot(
                uncheckedFn((ThrowingFunction<String, Date, ParseException>) createDateFormat(timeZone)::parse)
                    .apply(entry.getKey()),
                entry.getValue())));

    return result;
  }

  private DateFormat createDateFormat(String timeZone)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));

    return dateFormat;
  }

  private StockSnapshotAlphaVantage createStockSnapshot(Date timeStamp, JsonNode stockData)
  {
    StockSnapshotAlphaVantage stockSnapshot;
    try
    {
      stockSnapshot = objectMapper.treeToValue(stockData, StockSnapshotAlphaVantage.class);
      stockSnapshot.setTimeStamp(timeStamp);
      return stockSnapshot;
    }
    catch (JsonProcessingException e)
    {
      throw new RuntimeException(e);
    }
  }
}