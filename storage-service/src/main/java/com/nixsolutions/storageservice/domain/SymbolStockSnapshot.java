package com.nixsolutions.storageservice.domain;

import com.nixsolutions.financial.domain.SymbolStockSnapshotsBase;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("symbol_snapshot")
public class SymbolStockSnapshot extends SymbolStockSnapshotsBase
{
  @Id
  @Override
  public String getSymbol()
  {
    return super.getSymbol();
  }

  @CreatedDate
  @Override
  public Date getCreatedAt()
  {
    return super.getCreatedAt();
  }

  @LastModifiedDate
  @Override
  public Date getUpdatedAt()
  {
    return super.getUpdatedAt();
  }

  @Version
  @Override
  public Long getVersion()
  {
    return super.getVersion();
  }
}
