package me.nexters.doctor24.medical.hospital.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalType;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@Slf4j
@DataMongoTest
class HospitalRepositoryTest {

	@Autowired
	private HospitalRepository hospitalRepository;

	@BeforeEach
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

		GeoJsonPoint location = new GeoJsonPoint(127.0395873429168, 37.485612179925724);
		Hospital hospital = Hospital.builder()
			.name("토닥병원")
			.id("B1100027")
			.hospitalType(HospitalType.find("한방병원"))
			.days(days)
			.address("서울특별시 강남구 봉은사로 612 (삼성동)")
			.phone("02-2222-4888")
			.location(location)
			.build();

		Hospital saved = hospitalRepository.save(hospital).block();

		assertThat(hospital).isEqualTo(saved);
	}

	@Test
	void 근접_지점_검색() {
		Point point = new Point(127.0395873429168, 37.485612179925724);
		Distance distance = new Distance(0.5, Metrics.KILOMETERS);
		hospitalRepository.findByLocationNear(point, distance)
			.subscribe(System.out::println);
	}
}