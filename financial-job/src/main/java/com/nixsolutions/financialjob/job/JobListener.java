package com.nixsolutions.financialjob.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener
{
  private static final Logger LOG = LoggerFactory.getLogger(javax.batch.api.listener.JobListener.class);

  @Override
  public void beforeJob(JobExecution jobExecution)
  {
    LOG.debug("Job " + jobExecution.getJobConfigurationName() + "started at: " + jobExecution.getStartTime());
  }

  @Override
  public void afterJob(JobExecution jobExecution)
  {
    LOG.debug("Job " + jobExecution.getJobConfigurationName() + "finished at: " + jobExecution.getEndTime());
  }
}
