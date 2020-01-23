package me.nexters.doctor24.batch.config;

import java.util.HashMap;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.batch.processor.HospitalDetailProcessor;
import me.nexters.doctor24.batch.processor.HospitalProcessor;
import me.nexters.doctor24.batch.reader.HospitalReader;
import me.nexters.doctor24.batch.writer.HospitalDetailWriter;
import me.nexters.doctor24.batch.writer.HospitalWriter;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobConfig {
	private final JobRepository jobRepository;
	private final HospitalReader hospitalReader;
	private final HospitalProcessor hospitalProcessor;
	private final HospitalWriter hospitalWriter;
	private final MongoTemplate mongoTemplate;
	private final HospitalDetailProcessor hospitalDetailProcessor;
	private final HospitalDetailWriter hospitalDetailWriter;

	@Bean
	public JobBuilderFactory jobBuilderFactory() {
		return new JobBuilderFactory(jobRepository);
	}

	@Bean
	public Job medicalJob(JobBuilderFactory jobBuilderFactory, Step hospitalDetailStep) {
		return jobBuilderFactory.get("medicalJob")
			.preventRestart()
			.start(hospitalDetailStep)
			.build();
	}

	@Bean
	public Step hospitalStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("hospitalStep")
			.<List<HospitalBasicRaw>, List<Hospital>>chunk(1)
			.reader(hospitalReader)
			.processor(hospitalProcessor)
			.writer(hospitalWriter)
			.build();
	}

	@Bean
	public Step hospitalDetailStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("hospitalDetailStep")
			.<Hospital, Hospital>chunk(1000)
			.reader(hospitalDetailReader())
			.processor(hospitalDetailProcessor)
			.writer(hospitalDetailWriter)
			.build();
	}

	@Bean
	@StepScope
	public MongoItemReader<Hospital> hospitalDetailReader() {
		MongoItemReader<Hospital> reader = new MongoItemReader<>();
		reader.setTemplate(mongoTemplate);
		reader.setCollection("hospital");
		reader.setPageSize(1000);
		reader.setSort(new HashMap<>() {{
			put("_id", Sort.Direction.DESC);
		}});
		reader.setTargetType(Hospital.class);
		reader.setQuery(new Query());
		return reader;
	}
}
