package me.nexters.doctor24.batch.config;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.batch.config.listener.HospitalDetailFailureStepListener;
import me.nexters.doctor24.batch.config.listener.HospitalStepListener;
import me.nexters.doctor24.batch.config.util.JobParameterUtil;
import me.nexters.doctor24.batch.dto.HospitalRaw;
import me.nexters.doctor24.batch.dto.hospital.detail.HospitalDetailRaw;
import me.nexters.doctor24.batch.dto.pharmacy.PharmacyRaw;
import me.nexters.doctor24.batch.processor.HospitalDetailFailureProcessor;
import me.nexters.doctor24.batch.processor.HospitalProcessor;
import me.nexters.doctor24.batch.processor.PharmacyProcessor;
import me.nexters.doctor24.batch.reader.HospitalDetailFailureReader;
import me.nexters.doctor24.batch.reader.HospitalReader;
import me.nexters.doctor24.batch.reader.PharmacyReader;
import me.nexters.doctor24.batch.writer.*;
import me.nexters.domain.hospital.Hospital;
import me.nexters.domain.pharmacy.Pharmacy;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MedicalJobConfig {
	private static final int CHUNK_SIZE = 1000;

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final HospitalReader hospitalReader;
	private final HospitalProcessor hospitalProcessor;
	private final HospitalWriter hospitalWriter;
	private final HospitalDetailFailureReader hospitalDetailFailureReader;
	private final HospitalDetailFailureProcessor hospitalDetailFailureProcessor;
	private final HospitalDetailFailureWriter hospitalDetailFailureWriter;
	private final HospitalStepListener hospitalStepListener;
	private final HospitalDetailFailureStepListener hospitalDetailFailureStepListener;
	private final HospitalCleansingWriter hospitalCleansingWriter;
	private final PharmacyReader pharmacyReader;
	private final PharmacyProcessor pharmacyProcessor;
	private final PharmacyWriter pharmacyWriter;
	private final PharmacyCleansingWriter pharmacyCleansingWriter;
	private final MongoTemplate mongoTemplate;

	@Bean
	public Job medicalJob(Step hospitalStep, Step hospitalDetailFailureStep,
		Step hospitalCleansingStep, Step pharmacyStep, Step pharmacyCleansingStep) {
		return jobBuilderFactory.get("medicalJob")
			.preventRestart()
			.start(hospitalStep)
			.next(hospitalDetailFailureStep)
			.next(hospitalCleansingStep)
			.next(pharmacyStep)
			.next(pharmacyCleansingStep)
			.build();
	}

	@Bean
	@JobScope
	public Step hospitalStep() {
		return stepBuilderFactory.get("hospitalStep")
			.<HospitalRaw, List<Hospital>>chunk(1)
			.reader(hospitalReader)
			.processor(hospitalProcessor)
			.writer(hospitalWriter)
			.listener(hospitalStepListener)
			.build();
	}

	@Bean
	@JobScope
	public Step hospitalDetailFailureStep() {
		return stepBuilderFactory.get("hospitalDetailFailureStep")
			.<List<HospitalDetailRaw>, List<Hospital>>chunk(1)
			.reader(hospitalDetailFailureReader)
			.processor(hospitalDetailFailureProcessor)
			.writer(hospitalDetailFailureWriter)
			.listener(hospitalDetailFailureStepListener)
			.build();
	}

	@Bean
	@JobScope
	public Step hospitalCleansingStep() {
		return stepBuilderFactory.get("hospitalCleansingStep")
			.<Hospital, Hospital>chunk(CHUNK_SIZE)
			.reader(hospitalCleansingReader(null))
			.writer(hospitalCleansingWriter)
			.build();
	}

	@Bean
	@StepScope
	public MongoItemReader<Hospital> hospitalCleansingReader(@Value("#{jobParameters[startTime]}") Long startTime) {
		MongoItemReader<Hospital> reader = new MongoItemReader<>();
		reader.setTemplate(mongoTemplate);
		reader.setCollection("hospital");
		reader.setPageSize(CHUNK_SIZE);
		reader.setSort(new HashMap<>() {{
			put("_id", Sort.Direction.DESC);
		}});
		reader.setTargetType(Hospital.class);
		LocalDateTime threshold = JobParameterUtil.millsToLocalDateTime(startTime);
		Query query = new Query(where("rowWriteDate").lt(threshold));
		reader.setQuery(query);
		return reader;
	}

	@Bean
	@JobScope
	public Step pharmacyStep() {
		return stepBuilderFactory.get("pharmacyStep")
			.<List<PharmacyRaw>, List<Pharmacy>>chunk(1)
			.reader(pharmacyReader)
			.processor(pharmacyProcessor)
			.writer(pharmacyWriter)
			.build();
	}

	@Bean
	@JobScope
	public Step pharmacyCleansingStep() {
		return stepBuilderFactory.get("pharmacyCleansingStep")
			.<Pharmacy, Pharmacy>chunk(CHUNK_SIZE)
			.reader(pharmacyCleansingReader(null))
			.writer(pharmacyCleansingWriter)
			.build();
	}

	@Bean
	@StepScope
	public MongoItemReader<Pharmacy> pharmacyCleansingReader(@Value("#{jobParameters[startTime]}") Long startTime) {
		MongoItemReader<Pharmacy> reader = new MongoItemReader<>();
		reader.setTemplate(mongoTemplate);
		reader.setCollection("pharmacy");
		reader.setPageSize(CHUNK_SIZE);
		reader.setSort(new HashMap<>() {{
			put("_id", Sort.Direction.DESC);
		}});
		reader.setTargetType(Pharmacy.class);
		LocalDateTime threshold = JobParameterUtil.millsToLocalDateTime(startTime);
		Query query = new Query(where("rowWriteDate").lt(threshold).and("isManaged").is("false"));
		reader.setQuery(query);
		return reader;
	}
}
