package com.nixsolutions.financial.discovery.config

import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Import(FinancialDomainConfiguration::class)
annotation class EnableSimpleDiscovery
