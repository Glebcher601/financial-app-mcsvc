package com.nixsolutions.financialjob.configuration;

import javax.sql.DataSource;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer
{

  @Override
  public void setDataSource(@Autowired
                            @Qualifier("springBatchDataSource") DataSource dataSource)
  {
    //super.setDataSource(dataSource);
    // If we don't provide a datasource, an in-memory db will be used
  }

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
}
