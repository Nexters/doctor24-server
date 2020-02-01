package me.nexters.doctor24.batch.processor;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HospitalProcessorTest {
	@Autowired
	private HospitalProcessor hospitalProcessor;

	@Ignore
	void hospital_raw_를_hospital_로_파싱() {
//		List<Hospital> hospitals = hospitalProcessor.process(List.of(HospitalDataHelper.createHospitalBasicRaw()));
//		assertThat(hospitals.get(0).getId()).isEqualTo("A1100141");
	}
}