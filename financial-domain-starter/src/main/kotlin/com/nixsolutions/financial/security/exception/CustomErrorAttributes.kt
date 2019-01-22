package com.nixsolutions.financial.security.exception

import com.nixsolutions.financial.metrics.MeterRegistryAware
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class CustomErrorAttributes<T : Throwable>(override var meterRegistry: MeterRegistry) :
    DefaultErrorAttributes(), MeterRegistryAware {

  override fun getErrorAttributes(request: ServerRequest, includeStackTrace: Boolean): Map<String, Any> {
    val errorAttributes = super.getErrorAttributes(request, includeStackTrace)
    errorAttributes.remove("trace")

    Counter.builder("exceptions")
        .tag("type", getError(request)::class.java.name)
        .tag("path", errorAttributes["path"].toString())
        .register(meterRegistry)
        .increment()

    errorAttributes["status"] = HttpStatus.INTERNAL_SERVER_ERROR.value()
    errorAttributes["error"] = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
    return errorAttributes
  }
}
