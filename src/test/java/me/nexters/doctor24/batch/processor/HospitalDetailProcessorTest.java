package me.nexters.doctor24.batch.processor;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@SpringBootTest
class HospitalDetailProcessorTest {
	@Autowired
	private HospitalDetailProcessor hospitalDetailProcessor;

	@Test
	void hospital_detail_raw_파싱() {
		Hospital hospital = hospitalDetailProcessor.process(HospitalDataHelper.createHospitalWithoutCategories());
		assertThat(Objects.requireNonNull(hospital).getCategories()).isEqualTo(List.of(
			"내과", "마취통증의학과", "비뇨기과", "산부인과", "신경외과", "영상의학과", "외과", "재활의학과", "정형외과"));
	}
}