package com.nixsolutions.financial.domain;

import java.util.Date;
import lombok.Data;

@Data
public class BaseEntity
{
  private Long version;

  private Date createdAt;

  private Date updatedAt;
}
