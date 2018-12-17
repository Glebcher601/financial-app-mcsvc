package com.nixsolutions.financial.security.aspect;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.lang.reflect.Parameter;
import java.util.Optional;

@Component
@Deprecated
public class DefaultAuthorizationHeaderMethodParamIdentifier implements AuthorizationHeaderMethodParamIdentifier
{
  @Override
  public boolean test(Parameter methodParam)
  {
    return Optional.ofNullable(
        Optional.ofNullable(methodParam.getAnnotation(RequestHeader.class))
            .map(RequestHeader::name)
            .filter(StringUtils::isNotBlank)
            .orElse(methodParam.getName()))
        .filter(headerName -> StringUtils.equals(headerName, "Authorization"))
        .map(val -> true)
        .orElse(false);
  }
}
