package com.nixsolutions.financial.metrics

import org.springframework.web.server.ServerWebExchange

const val PATH_TAG = "path"

fun requestPathMetrics(serverWebExchange: ServerWebExchange) =
  arrayOf(PATH_TAG, serverWebExchange.request.path.pathWithinApplication().value().substringBefore('?'))