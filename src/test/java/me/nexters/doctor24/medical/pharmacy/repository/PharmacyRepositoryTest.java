package me.nexters.doctor24.medical.pharmacy.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

@DataMongoTest
class PharmacyRepositoryTest {
	@Autowired
	private PharmacyRepository pharmacyRepository;

	@Test
	void 약국_도메인_저장() {
		List<Day> days = List.of(
			Day.of(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.SATURDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(DayOfWeek.SUNDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
		);

		Pharmacy pharmacy = Pharmacy.builder()
			.id("C1800232")
			.days(days)
			.latitude(36.5195854195)
			.longitude(127.2353413861)
			.name("고운길온누리약국")
			.phone("070-7716-7577")
			.build();

		Pharmacy saved = pharmacyRepository.save(pharmacy);

		assertThat(pharmacy).isEqualTo(saved);
	}
}