package com.nixsolutions.financial.security.config

import org.springframework.context.annotation.ImportSelector
import org.springframework.core.annotation.AnnotationAttributes
import org.springframework.core.type.AnnotationMetadata

class JwtAuthorizationConfigurationImportSelector : ImportSelector {
  override fun selectImports(importingClassMetadata: AnnotationMetadata): Array<String> {
    val annotationAttributes = AnnotationAttributes.fromMap(
        importingClassMetadata.getAnnotationAttributes(EnableJwtProtection::class.java.name))
    val useDefault = annotationAttributes?.getBoolean("useDefault")!!

    return if (useDefault)
      arrayOf(DefaultJwtAuthorizationConfiguration::class.qualifiedName!!)
    else
      arrayOf(CommonJwtSecurityConfiguration::class.qualifiedName!!)
  }
}