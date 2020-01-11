package me.nexters.doctor24.medical.hospital.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HospitalTypeTest {
	@Test
	void 종합_병원_타입_반환() {
		HospitalType hospitalType = HospitalType.find("종합병원");
		assertThat(hospitalType.getType()).isEqualTo(HospitalType.GENERAL.getType());
	}

	@Test
	void 알수없는_병원타입_반환() {
		HospitalType hospitalType = HospitalType.find("알수없는병원");
		assertThat(hospitalType.getType()).isEqualTo(HospitalType.UNKNOWN.getType());
	}
}