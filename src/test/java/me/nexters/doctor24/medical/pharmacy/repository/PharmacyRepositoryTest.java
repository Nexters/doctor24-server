package me.nexters.doctor24.medical.pharmacy.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

@DataMongoTest
class PharmacyRepositoryTest {
	@Autowired
	private PharmacyRepository pharmacyRepository;

	@Test
	void 약국_도메인_저장() {
		List<Day> days = List.of(
			Day.of(Day.DayType.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(Day.DayType.TUESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(Day.DayType.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(Day.DayType.THURSDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(Day.DayType.FRIDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(Day.DayType.SATURDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
			Day.of(Day.DayType.SUNDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
		);

		Pharmacy pharmacy = Pharmacy.builder()
			.id("C1800232")
			.days(days)
			.location(new GeoJsonPoint(127.2353413861, 36.5195854195))
			.name("고운길온누리약국")
			.phone("070-7716-7577")
			.build();

		Pharmacy saved = pharmacyRepository.save(pharmacy).block();

		assertThat(pharmacy).isEqualTo(saved);
	}
}