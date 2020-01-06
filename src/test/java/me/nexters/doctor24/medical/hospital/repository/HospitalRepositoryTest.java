package me.nexters.doctor24.medical.hospital.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import me.nexters.doctor24.medical.hospital.model.mongo.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalType;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@DataMongoTest
class HospitalRepositoryTest {

	@Autowired
	private HospitalRepository hospitalRepository;

	@Test
	void 병원_도메인_저장() {
		List<Day> days = List.of(
			Day.of(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.SUNDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
		);

		Hospital hospital = Hospital.builder()
			.id("B1100027")
			.hospitalType(HospitalType.find("한방병원"))
			.days(days)
			.address("서울특별시 강남구 봉은사로 612 (삼성동)")
			.latitude(37.514279685612216)
			.longitude(127.06214779521632)
			.phone("02-2222-4888")
			.build();

		Hospital saved = hospitalRepository.save(hospital);

		assertThat(hospital).isEqualTo(saved);
	}
}