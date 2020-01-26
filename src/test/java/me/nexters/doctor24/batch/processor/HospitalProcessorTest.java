package me.nexters.doctor24.batch.processor;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@SpringBootTest
class HospitalProcessorTest {
	@Autowired
	private HospitalProcessor hospitalProcessor;

	@Test
	void hospital_raw_를_hospital_로_파싱() {
		List<Hospital> hospitals = hospitalProcessor.process(List.of(HospitalDataHelper.createHospitalBasicRaw()));
		assertThat(hospitals.get(0).getId()).isEqualTo("A1100141");
	}
}