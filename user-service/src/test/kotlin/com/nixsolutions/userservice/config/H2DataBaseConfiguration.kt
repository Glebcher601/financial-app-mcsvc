package com.nixsolutions.userservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource


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

    return dataSource
  }
}