package com.nixsolutions.financialjob;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.nixsolutions.financialjob.configuration.ApiUrlConfiguration;
import com.nixsolutions.financialjob.service.DataPullService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinancialJobApplicationTests
{

  @Autowired
  ApiUrlConfiguration apiUrlConfiguration;

  @Autowired
  @Qualifier("prefilledTemplateUrl")
  String urlPrefilled;

  @Autowired
  DataPullService dataPullService;

  @Test
  public void contextLoads()
  {
    dataPullService.pullSnapshotsForSymbol("MSFT");
  }

}
