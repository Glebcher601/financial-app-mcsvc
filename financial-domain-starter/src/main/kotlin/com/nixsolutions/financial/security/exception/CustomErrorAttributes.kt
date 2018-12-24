package com.nixsolutions.financial.security.exception

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class CustomErrorAttributes<T : Throwable> : DefaultErrorAttributes() {

  override fun getErrorAttributes(request: ServerRequest, includeStackTrace: Boolean): Map<String, Any> {
    val errorAttributes = super.getErrorAttributes(request, includeStackTrace)
    errorAttributes.remove("trace")

    errorAttributes["status"] = HttpStatus.INTERNAL_SERVER_ERROR.toString()
    errorAttributes["error"] = HttpStatus.FORBIDDEN.reasonPhrase
    return errorAttributes
  }
}
