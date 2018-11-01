package com.nixsolutions.financialjob.configuration;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig
{
  @Bean(name = "springBatchDataSource")
  public DataSource springBatchDataSource()
  {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:mem:batch_meta;DB_CLOSE_DELAY=-1");
    dataSource.setUsername("sa");
    dataSource.setPassword("sa");

    return dataSource;
  }


  @Bean
  public TaskExecutor taskExecutor()
  {
    return new SimpleAsyncTaskExecutor();
  }

  @Bean
  public PartitionHandler multiThreadPartitionHandler(@Value("${job.properties.threads}") Integer threadsCount,
                                                      TaskExecutor taskExecutor)
  {
    TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();

    partitionHandler.setTaskExecutor(taskExecutor);
    partitionHandler.setGridSize(threadsCount);

    return partitionHandler;
  }
}
