package me.nexters.doctor24.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobScheduler {
	private final Job medicalJob;
	private final JobLauncher jobLauncher;

	@Scheduled(cron = "0 0 01 * * ?")
	public void getMedicalJob() {
		try {
			JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startTime", System.currentTimeMillis())
				.toJobParameters();
			JobExecution jobExecution = jobLauncher.run(medicalJob, jobParameters);
			log.info("job 종료 시간 : {}", jobExecution.getEndTime());
		} catch (JobExecutionException e) {
			log.error("job 을 실행하는데 실패하였습니다 msg : {}", e.getMessage());
		}
	}
}
