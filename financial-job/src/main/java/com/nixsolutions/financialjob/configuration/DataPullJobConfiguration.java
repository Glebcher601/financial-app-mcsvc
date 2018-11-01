package com.nixsolutions.financialjob.configuration;

import static com.google.common.collect.Lists.newArrayList;
import static com.nixsolutions.financialjob.configuration.ApiUrlConfiguration.UriParams.API_KEY;
import static com.nixsolutions.financialjob.configuration.ApiUrlConfiguration.UriParams.FUNCTION;
import static com.nixsolutions.financialjob.configuration.ApiUrlConfiguration.UriParams.INTERVAL;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.nixsolutions.financial.domain.SymbolStockSnapshots;
import com.nixsolutions.financialjob.job.JobListener;
import com.nixsolutions.financialjob.job.ListDataSplitter;
import com.nixsolutions.financialjob.service.DataPullService;

//TODO Metrics or logs to add
@Configuration
public class DataPullJobConfiguration
{
  private static final Logger LOG = LoggerFactory.getLogger(DataPullJobConfiguration.class);

  @Autowired
  private JobBuilderFactory jobs;

  @Autowired
  private StepBuilderFactory steps;

  @Bean
  public String prefilledTemplateUrl(ApiUrlConfiguration apiProps)
  {
    return apiProps.getTemplateUrl()
        .replace("{" + API_KEY + "}", apiProps.getKey())
        .replace("{" + FUNCTION + "}", apiProps.getFunction())
        .replace("{" + INTERVAL + "}", apiProps.getInterval());
  }

  @Bean
  public Job dataPull(PullStepConfiguration pullStepConfiguration,
                      Step slaveStep,
                      JobListener jobListener)
  {
    Step pullStep = steps.get("pullData")
        .partitioner("slaveStep", pullStepConfiguration.symbolPartitioner(null, null))
        .step(slaveStep)
        .build();

    return jobs.get("dataPullJob")
        .listener(jobListener)
        .start(pullStep)
        .build();
  }

  @Bean
  public Step slaveStep(ItemReader<String> reader,
                        ItemProcessor<String, SymbolStockSnapshots> processor,
                        ItemWriter<SymbolStockSnapshots> writer)
  {
    return steps.get("slaveStep").<String, SymbolStockSnapshots>chunk(1)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .build();
  }

  @Bean
  @StepScope
  public ItemReader<String> symbolReader(@Value("#{stepExecutionContext[data]}") List<String> symbols)
  {
    return () ->
    {
      if (CollectionUtils.isEmpty(symbols))
      {
        return null;
      }

      return symbols.remove(0);
    };
  }

  @Bean
  @StepScope
  public ItemProcessor<String, SymbolStockSnapshots> symbolProcessor(DataPullService dataPullService)
  {
    return dataPullService::pullSnapshotsForSymbol;
  }

  @Bean
  @StepScope
  public ItemWriter<SymbolStockSnapshots> stockSnapshotsWriter()
  {
    return items ->
    {
      LOG.debug("Written snapshot items for symbol: " + items.get(0).getSymbol());
      //TODO write to stock service
    };
  }

  @Configuration
  public static class PullStepConfiguration
  {

    @Bean
    @StepScope
    public Partitioner symbolPartitioner(ApiUrlConfiguration apiUrlProps, ListDataSplitter listDataSplitter)
    {
      return gridSize -> listDataSplitter.splitData(newArrayList(apiUrlProps.getSymbols()), gridSize);
    }
  }
}
