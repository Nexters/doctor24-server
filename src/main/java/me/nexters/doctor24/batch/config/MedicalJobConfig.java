package me.nexters.doctor24.batch.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.batch.config.listener.HospitalDetailFailureStepListener;
import me.nexters.doctor24.batch.config.listener.HospitalStepListener;
import me.nexters.doctor24.batch.processor.HospitalDetailFailureProcessor;
import me.nexters.doctor24.batch.processor.HospitalProcessor;
import me.nexters.doctor24.batch.processor.InvalidHospitalProcessor;
import me.nexters.doctor24.batch.processor.InvalidPharmacyProcessor;
import me.nexters.doctor24.batch.processor.PharmacyProcessor;
import me.nexters.doctor24.batch.reader.HospitalDetailFailureReader;
import me.nexters.doctor24.batch.reader.HospitalReader;
import me.nexters.doctor24.batch.reader.PharmacyReader;
import me.nexters.doctor24.batch.writer.HospitalDetailFailureWriter;
import me.nexters.doctor24.batch.writer.HospitalWriter;
import me.nexters.doctor24.batch.writer.InvalidHospitalWriter;
import me.nexters.doctor24.batch.writer.InvalidPharmacyWriter;
import me.nexters.doctor24.batch.writer.PharmacyWriter;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MedicalJobConfig {
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
	private final InvalidHospitalProcessor invalidHospitalProcessor;
	private final InvalidHospitalWriter invalidHospitalWriter;
	private final PharmacyReader pharmacyReader;
	private final PharmacyProcessor pharmacyProcessor;
	private final PharmacyWriter pharmacyWriter;
	private final InvalidPharmacyProcessor invalidPharmacyProcessor;
	private final InvalidPharmacyWriter invalidPharmacyWriter;
	private final MongoTemplate mongoTemplate;

	@Bean
	public Job medicalJob(Step hospitalStep, Step hospitalDetailFailureStep,
		Step removeInvalidHospitalStep, Step pharmacyStep, Step removeInvalidPharmacyStep) {
		return jobBuilderFactory.get("medicalJob")
			.preventRestart()
			.start(hospitalStep)
			.next(hospitalDetailFailureStep)
			.next(pharmacyStep)
			.build();
	}

	@Bean
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
	public Step removeInvalidHospitalStep() {
		return stepBuilderFactory.get("removeInvalidHospitalStep")
			.<Hospital, Hospital>chunk(1000)
			.reader(invalidHospitalReader())
			.processor(invalidHospitalProcessor)
			.writer(invalidHospitalWriter)
			.build();
	}

	@Bean
	@StepScope
	public MongoItemReader<Hospital> invalidHospitalReader() {
		MongoItemReader<Hospital> reader = new MongoItemReader<>();
		reader.setTemplate(mongoTemplate);
		reader.setCollection("hospital");
		reader.setPageSize(1000);
		reader.setSort(new HashMap<>() {{
			put("_id", Sort.Direction.DESC);
		}});
		reader.setTargetType(Hospital.class);
		LocalDateTime threshold = LocalDateTime.now().minusHours(12);
		Criteria criteria = Criteria.where("rowWriteDate").lt(threshold);
		Query query = new Query(criteria);
		reader.setQuery(query);
		return reader;
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
	public Step removeInvalidPharmacyStep(MongoItemReader<Pharmacy> invalidPharmacyReader) {
		return stepBuilderFactory.get("removeInvalidPharmacyStep")
			.<Pharmacy, Pharmacy>chunk(1000)
			.reader(invalidPharmacyReader)
			.processor(invalidPharmacyProcessor)
			.writer(invalidPharmacyWriter)
			.build();
	}

	@Bean
	@StepScope
	public MongoItemReader<Pharmacy> invalidPharmacyReader() {
		MongoItemReader<Pharmacy> reader = new MongoItemReader<>();
		reader.setTemplate(mongoTemplate);
		reader.setCollection("pharmacy");
		reader.setPageSize(1000);
		reader.setSort(new HashMap<>() {{
			put("_id", Sort.Direction.DESC);
		}});
		reader.setTargetType(Pharmacy.class);
		LocalDateTime threshold = LocalDateTime.now().minusHours(12);
		Criteria criteria = Criteria.where("rowWriteDate").lt(threshold);
		Query query = new Query(criteria);
		reader.setQuery(query);
		return reader;
	}
}
