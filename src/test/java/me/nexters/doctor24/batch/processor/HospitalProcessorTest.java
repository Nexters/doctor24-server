package me.nexters.doctor24.batch.processor;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Objects;

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
		assertThat(Objects.requireNonNull(hospitals).get(0).getCategories()).isEqualTo("내과,비뇨기과,성형외과,소아청소년과,피부과");
		assertThat(hospitals.get(0).getId()).isEqualTo("A1100141");
	}
}