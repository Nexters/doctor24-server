package me.nexters.doctor24.batch.config;

import me.nexters.domain.hospital.HospitalRepository;
import org.junit.Ignore;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static org.assertj.core.api.Assertions.assertThat;

@EnableReactiveMongoRepositories(basePackages = "me.nexters.domain")
@SpringBootTest
class JobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private HospitalRepository hospitalRepository;

    private JobParameters jobParameters = new JobParametersBuilder()
            .addLong("startTime", System.currentTimeMillis())
            .toJobParameters();

    @Ignore
    void 병원_전체_배치() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        System.out.println(hospitalRepository.count());
    }
}