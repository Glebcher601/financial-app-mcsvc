package com.nixsolutions.financialjob.job;

import java.util.Date;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableBatchProcessing
@EnableScheduling
@Configuration
public class JobScheduler
{
  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job dataPullJob;

  @Scheduled(cron = "${job.properties.cron}")
  public void dailyScheduler() throws Exception
  {
    JobParameters jobParameters = new JobParametersBuilder().addDate("dateStarted", new Date())
        .toJobParameters();

    JobExecution jobExecution = jobLauncher.run(dataPullJob, jobParameters);
  }
}
