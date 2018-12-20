package com.nixsolutions.financial.security.exception

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.*

@Component
class CustomErrorAttributes<T : Throwable> : DefaultErrorAttributes() {

  override fun getErrorAttributes(request: ServerRequest, includeStackTrace: Boolean): Map<String, Any> {
    val errorAttributes = super.getErrorAttributes(request, includeStackTrace)
    errorAttributes.remove("trace")

    if (SECURITY_EXCEPTIONS.contains(getError(request)::class.java)) {
      errorAttributes["status"] = HttpStatus.FORBIDDEN.value()
      errorAttributes["error"] = HttpStatus.FORBIDDEN.reasonPhrase
      return errorAttributes
    }

    return errorAttributes
  }

  companion object {
    private val SECURITY_EXCEPTIONS = Arrays.asList<Class<out Exception>>(
        InvalidTokenException::class.java,
        NoAccessException::class.java,
        TokenExpiredException::class.java
    )
  }
}
