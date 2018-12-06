package com.nixsolutions.financial.security.aspect;

import java.lang.reflect.Parameter;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;

public class DefaultAuthorizationHeaderMethodParamIdentifier implements AuthorizationHeaderMethodParamIdentifier
{
  @Override
  public boolean test(Parameter methodParam)
  {
    Optional<RequestHeader> annotation = Optional.ofNullable(methodParam.getAnnotation(RequestHeader.class));
    return annotation.map(RequestHeader::name)
        .filter(StringUtils::isNotBlank)
        .flatMap(val -> Optional.of(true))
        .orElse(false);
  }
}
