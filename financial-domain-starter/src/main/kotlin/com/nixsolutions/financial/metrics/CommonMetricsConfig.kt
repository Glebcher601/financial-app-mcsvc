package com.nixsolutions.financial.metrics

import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonMetricsConfig {
  @Bean
  fun meterRegistryCustomizer(@Value("\${spring.application.name:UNKNOWN}") appName: String):
      MeterRegistryCustomizer<MeterRegistry> {
    return MeterRegistryCustomizer { it.config().commonTags("svcName", appName) };
  }

  @Bean
  fun timedAspect(meterRegistry: MeterRegistry) = TimedAspect(meterRegistry)
}

interface MeterRegistryAware {
  var meterRegistry: MeterRegistry
}