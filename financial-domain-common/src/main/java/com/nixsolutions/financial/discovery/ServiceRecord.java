package com.nixsolutions.financial.discovery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ServiceRecord
{
  @NotBlank
  private String name;

  @NotNull
  private DiscoveryType resolution;

  private String version;

  private String port = "8080";
}