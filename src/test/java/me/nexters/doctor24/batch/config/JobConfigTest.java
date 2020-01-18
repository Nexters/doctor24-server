package me.nexters.doctor24.batch.config;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

@SpringBootTest
class JobConfigTest {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private HospitalRepository hospitalRepository;

	private JobParameters jobParameters = new JobParametersBuilder()
		.addLong("time", System.currentTimeMillis())
		.toJobParameters();

	@Test
	void 병원_전체_배치() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		System.out.println(hospitalRepository.count());
	}
}