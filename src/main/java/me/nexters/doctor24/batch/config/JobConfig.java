package me.nexters.doctor24.batch.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.batch.processor.HospitalProcessor;
import me.nexters.doctor24.batch.reader.HospitalReader;
import me.nexters.doctor24.batch.writer.HospitalWriter;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobConfig {
	private final JobRepository jobRepository;
	private final HospitalReader hospitalReader;
	private final HospitalProcessor hospitalProcessor;
	private final HospitalWriter hospitalWriter;

	@Bean
	public JobBuilderFactory jobBuilderFactory() {
		return new JobBuilderFactory(jobRepository);
	}

	@Bean
	public Job medicalJob(JobBuilderFactory jobBuilderFactory, Step hospitalStep) {
		return jobBuilderFactory.get("medicalJob")
			.preventRestart()
			.start(hospitalStep)
			.build();
	}

	@Bean
	public Step hospitalStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("hospitalStep")
			.<List<HospitalRaw>, List<Hospital>>chunk(1)
			.reader(hospitalReader)
			.processor(hospitalProcessor)
			.writer(hospitalWriter)
			.build();
	}
}
