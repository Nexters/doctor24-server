package me.nexters.doctor24.invoker;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.HospitalType;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalInquires;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

@Slf4j
@SpringBootTest
class HospitalRawInquiresTest {
	@Autowired
	private HospitalInquires hospitalInquires;
	@Autowired
	private HospitalRepository hospitalRepository;

	private int migrationCount = 0;

	@Test
	void 전국_병원_인덱스() {
		PageResponse<HospitalRaw> hospitalPage =
			hospitalInquires.getHospitalPage(PageRequest.of(2, 100));
		assertThat(hospitalPage.getContents().size(), is(100));
	}

	@Test
	void 강남구_병원_호출() {
		PageResponse<HospitalRaw> hospitalsByCityAndProvinceOrderBy =
			hospitalInquires.getHospitalsByCityAndProvinceOrderBy(
				PageRequest.of(1, 100), "서울특별시", "강남구");

		assertThat(hospitalsByCityAndProvinceOrderBy.getContents().size(), is(100));
	}

	//@Test
	void 병원_전체_목록_마이그레이션() {
		PageResponse<HospitalRaw> hospitalPage =
			hospitalInquires.getHospitalPage(PageRequest.of(1, 250));
		List<HospitalRaw> hospitals = new ArrayList<>(hospitalPage.getContents());
		while (hospitalPage.hasNext()) {
			hospitalPage =
				hospitalInquires.getHospitalPage(PageRequest.of(hospitalPage.getNextPage(), 250));
			hospitals.addAll(hospitalPage.getContents());
		}

		System.out.println(hospitals.size());

		System.out.println("----- start migration -----");

		hospitals.parallelStream()
			.map(this::hospitalParser)
			.forEach(item -> {
				if (Objects.nonNull(item)) {
					hospitalRepository.save(item).block();
					migrationCount++;
				}
			});

		System.out.println("success migration count " + migrationCount);
	}

	private Hospital hospitalParser(HospitalRaw hospitalRaw) {
		try {
			List<Day> days = new ArrayList<>();
			if (Objects.nonNull(hospitalRaw.getMondayStart()) && Objects.nonNull(hospitalRaw.getMondayClose())) {
				days.add(Day.of(Day.DayType.MONDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4)))));
			}

			if (Objects.nonNull(hospitalRaw.getTuesdayStart()) && Objects.nonNull(hospitalRaw.getTuesdayClose())) {
				days.add(Day.of(Day.DayType.TUESDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getTuesdayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getTuesdayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getTuesdayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getTuesdayClose().substring(2, 4)))));
			}

			if (Objects.nonNull(hospitalRaw.getWednesdayStart()) && Objects.nonNull(hospitalRaw.getWednesdayClose())) {
				days.add(Day.of(Day.DayType.WEDNESDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getWednesdayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getWednesdayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getWednesdayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getWednesdayClose().substring(2, 4)))));
			}

			if (Objects.nonNull(hospitalRaw.getThursdayStart()) && Objects.nonNull(hospitalRaw.getThursdayClose())) {
				days.add(Day.of(Day.DayType.THURSDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getThursdayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getThursdayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getThursdayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getThursdayClose().substring(2, 4)))));
			}

			if (Objects.nonNull(hospitalRaw.getFridayStart()) && Objects.nonNull(hospitalRaw.getFridayClose())) {
				days.add(Day.of(Day.DayType.FRIDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getFridayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getFridayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getFridayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getFridayClose().substring(2, 4)))));
			}

			if (Objects.nonNull(hospitalRaw.getSaturdayStart()) && Objects.nonNull(hospitalRaw.getSaturdayClose())) {
				days.add(Day.of(Day.DayType.SATURDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getSaturdayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getSaturdayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getSaturdayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getSaturdayClose().substring(2, 4)))));
			}

			if (Objects.nonNull(hospitalRaw.getSundayStart()) && Objects.nonNull(hospitalRaw.getSundayClose())) {
				days.add(Day.of(Day.DayType.SUNDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getSundayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getSundayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getSundayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getSundayClose().substring(2, 4)))));
			}

			if (Objects.nonNull(hospitalRaw.getHolidayStart()) && Objects.nonNull(hospitalRaw.getHolidayClose())) {
				days.add(Day.of(Day.DayType.HOLIDAY,
					LocalTime.of(Integer.parseInt(hospitalRaw.getHolidayStart().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getHolidayStart().substring(2, 4))),
					LocalTime.of(Integer.parseInt(hospitalRaw.getHolidayClose().substring(0, 2)),
						Integer.parseInt(hospitalRaw.getHolidayClose().substring(2, 4)))));
			}

			return Hospital.builder()
				.id(hospitalRaw.getId())
				.name(hospitalRaw.getName())
				.location(new GeoJsonPoint(hospitalRaw.getLongitude(), hospitalRaw.getLatitude()))
				.phone(hospitalRaw.getDutyTel1())
				.address(hospitalRaw.getDutyAddr())
				.hospitalType(HospitalType.find(hospitalRaw.getDutyDivNam()))
				.days(days)
				.build();
		} catch (Exception e) {
			log.error("[FAIL TO MIGRATION] {} {}", hospitalRaw.getId(), e.getMessage());
			return null;
		}
	}
}