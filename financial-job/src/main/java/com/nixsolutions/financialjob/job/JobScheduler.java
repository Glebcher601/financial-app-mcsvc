package com.nixsolutions.financialjob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@EnableScheduling
@Component
public class JobScheduler
{
  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job dataPullJob;

  @Scheduled(cron = "${job.properties.cron}")
  public void dailyScheduler()
  {
    JobParameters jobParameters = new JobParametersBuilder().addDate("dateStarted", new Date())
        .toJobParameters();

    try
    {
      JobExecution jobExecution = jobLauncher.run(dataPullJob, jobParameters);

    } catch (JobExecutionAlreadyRunningException |
        JobInstanceAlreadyCompleteException |
        JobParametersInvalidException |
        JobRestartException e)
    {
      e.printStackTrace();
    }

  }
}
