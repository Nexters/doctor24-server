package me.nexters.doctor24.batch.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.batch.config.listener.HospitalDetailFailureStepListener;
import me.nexters.doctor24.batch.config.listener.HospitalDetailStepListener;
import me.nexters.doctor24.batch.config.listener.HospitalStepListener;
import me.nexters.doctor24.batch.config.util.JobParameterUtil;
import me.nexters.doctor24.batch.processor.HospitalDetailFailureProcessor;
import me.nexters.doctor24.batch.processor.HospitalDetailProcessor;
import me.nexters.doctor24.batch.processor.HospitalProcessor;
import me.nexters.doctor24.batch.processor.InvalidHospitalProcessor;
import me.nexters.doctor24.batch.processor.PharmacyProcessor;
import me.nexters.doctor24.batch.reader.HospitalDetailFailureReader;
import me.nexters.doctor24.batch.reader.HospitalReader;
import me.nexters.doctor24.batch.reader.PharmacyReader;
import me.nexters.doctor24.batch.writer.HospitalDetailFailureWriter;
import me.nexters.doctor24.batch.writer.HospitalDetailWriter;
import me.nexters.doctor24.batch.writer.HospitalWriter;
import me.nexters.doctor24.batch.writer.InvalidHospitalWriter;
import me.nexters.doctor24.batch.writer.PharmacyWriter;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JobConfig {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final HospitalReader hospitalReader;
	private final HospitalProcessor hospitalProcessor;
	private final HospitalWriter hospitalWriter;
	private final MongoTemplate mongoTemplate;
	private final HospitalDetailProcessor hospitalDetailProcessor;
	private final HospitalDetailWriter hospitalDetailWriter;
	private final HospitalDetailFailureReader hospitalDetailFailureReader;
	private final HospitalDetailFailureProcessor hospitalDetailFailureProcessor;
	private final HospitalDetailFailureWriter hospitalDetailFailureWriter;
	private final HospitalDetailStepListener hospitalDetailStepListener;
	private final HospitalStepListener hospitalStepListener;
	private final HospitalDetailFailureStepListener hospitalDetailFailureStepListener;
	private final PharmacyReader pharmacyReader;
	private final PharmacyProcessor pharmacyProcessor;
	private final PharmacyWriter pharmacyWriter;
	private final InvalidHospitalProcessor invalidHospitalProcessor;
	private final InvalidHospitalWriter invalidHospitalWriter;

	@Bean
	public Job medicalJob(Step hospitalStep, Step hospitalDetailStep, Step hospitalDetailFailureStep,
		Step pharmacyStep, Step removeInvalidHospitalStep) {
		return jobBuilderFactory.get("medicalJob")
			.preventRestart()
			.start(hospitalStep)
			.next(hospitalDetailStep)
			.next(hospitalDetailFailureStep)
			.next(pharmacyStep)
			.next(removeInvalidHospitalStep)
			.build();
	}

	@Bean
	public Step hospitalStep() {
		return stepBuilderFactory.get("hospitalStep")
			.<List<HospitalBasicRaw>, List<Hospital>>chunk(1)
			.reader(hospitalReader)
			.processor(hospitalProcessor)
			.writer(hospitalWriter)
			.listener(hospitalStepListener)
			.build();
	}

	@Bean
	public Step hospitalDetailStep() {
		return stepBuilderFactory.get("hospitalDetailStep")
			.<Hospital, Hospital>chunk(1000)
			.reader(hospitalDetailReader())
			.processor(hospitalDetailProcessor)
			.writer(hospitalDetailWriter)
			.listener(hospitalDetailStepListener)
			.build();
	}

	@Bean
	public Step hospitalDetailFailureStep() {
		return stepBuilderFactory.get("hospitalDetailFailureStep")
			.<Map<String, Hospital>, List<Hospital>>chunk(1)
			.reader(hospitalDetailFailureReader)
			.processor(hospitalDetailFailureProcessor)
			.writer(hospitalDetailFailureWriter)
			.listener(hospitalDetailFailureStepListener)
			.build();
	}

	@Bean
	public Step pharmacyStep() {
		return stepBuilderFactory.get("pharmacyStep")
			.<List<PharmacyRaw>, List<Pharmacy>>chunk(1)
			.reader(pharmacyReader)
			.processor(pharmacyProcessor)
			.writer(pharmacyWriter)
			.build();
	}

	@Bean
	public Step removeInvalidHospitalStep() {
		return stepBuilderFactory.get("removeInvalidHospitalStep")
			.<Hospital, Hospital>chunk(1000)
			.reader(invalidHospitalDetailReader(null))
			.processor(invalidHospitalProcessor)
			.writer(invalidHospitalWriter)
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
		Query emptyQuery = new Query();
		reader.setQuery(emptyQuery);
		return reader;
	}

	@Bean
	@StepScope
	public MongoItemReader<Hospital> invalidHospitalDetailReader(@Value("#{jobParameters[time]}") Long time) {
		MongoItemReader<Hospital> reader = new MongoItemReader<>();
		reader.setTemplate(mongoTemplate);
		reader.setCollection("hospital");
		reader.setPageSize(1000);
		reader.setSort(new HashMap<>() {{
			put("_id", Sort.Direction.DESC);
		}});
		reader.setTargetType(Hospital.class);
		LocalDateTime jobStartTime = JobParameterUtil.millsToLocalDateTime(time);
		Criteria criteria = Criteria.where("rowWriteDate").lt(jobStartTime);
		Query query = new Query(criteria);
		reader.setQuery(query);
		return reader;
	}
}
