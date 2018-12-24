package com.nixsolutions.financial.security.config

import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Import(JwtAuthorizationConfigurationImportSelector::class)
annotation class EnableJwtProtection(val useDefault: Boolean = true)