package com.nixsolutions.financial.security;

import com.nixsolutions.financial.security.aspect.JwtProtected;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
public class SecuredService
{

  public static final String ADMIN_RESOURCE = "Admin_resource";

  @JwtProtected(roles = "ADMIN")
  public String adminProtected(@RequestHeader String Authorization)
  {
    return ADMIN_RESOURCE;
  }
}
