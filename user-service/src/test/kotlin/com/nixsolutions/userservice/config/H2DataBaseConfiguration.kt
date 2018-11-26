package com.nixsolutions.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator


@Profile("test")
@Configuration
class H2DataBaseConfiguration {

  @Primary
  @Bean
  fun dataSource(): DriverManagerDataSource {
    val dataSource = DriverManagerDataSource()
    dataSource.setDriverClassName("org.h2.Driver")
    dataSource.url = "jdbc:h2:~/myDB;MV_STORE=false"
    dataSource.username = "sa"
    dataSource.password = ""

    val initSchema = ClassPathResource("test-sql/schema/schema.sql")
    val initData = ClassPathResource("test-sql/data/data.sql")
    val databasePopulator = ResourceDatabasePopulator(initSchema, initData)
    DatabasePopulatorUtils.execute(databasePopulator, dataSource)

    return dataSource
  }
}