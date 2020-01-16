package me.nexters.doctor24.batch.processor;

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
	void hospital_raw_를_hospital_로_파싱() throws Exception {
		List<Hospital> process = hospitalProcessor.process(List.of(HospitalDataHelper.createHospitalRaw()));
		System.out.println(process);
	}
}