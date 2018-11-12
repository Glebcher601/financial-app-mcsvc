package com.nixsolutions.financial.discovery;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceRecord
{
  /**
   * Defines the name of a service
   */
  @NotBlank
  private String name;

  /**
   * Defines how to connect to service
   */
  @NotNull
  private DiscoveryType resolution;

  /**
   * Version is not used for now
   */
  private String version;

  /**
   * Self-explaining
   */
  private String port = "8080";
}