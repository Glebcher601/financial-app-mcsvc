package com.nixsolutions.financialjob.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener
{
  private static final Logger LOG = LoggerFactory.getLogger(JobListener.class);

  @Override
  public void beforeJob(JobExecution jobExecution)
  {
  }

  @Override
  public void afterJob(JobExecution jobExecution)
  {
    LOG.debug("Job " + jobExecution + "finished at: " + jobExecution.getEndTime());
  }
}
