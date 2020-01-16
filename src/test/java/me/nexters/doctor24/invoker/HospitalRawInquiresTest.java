package me.nexters.doctor24.invoker;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.processor.util.OpeningHourParser;
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
					OpeningHourParser.parse(hospitalRaw.getMondayStart()),
					OpeningHourParser.parse(hospitalRaw.getMondayClose())));
			}

			if (Objects.nonNull(hospitalRaw.getTuesdayStart()) && Objects.nonNull(hospitalRaw.getTuesdayClose())) {
				days.add(Day.of(Day.DayType.TUESDAY,
					OpeningHourParser.parse(hospitalRaw.getTuesdayStart()),
					OpeningHourParser.parse(hospitalRaw.getTuesdayClose())));
			}

			if (Objects.nonNull(hospitalRaw.getWednesdayStart()) && Objects.nonNull(hospitalRaw.getWednesdayClose())) {
				days.add(Day.of(Day.DayType.WEDNESDAY,
					OpeningHourParser.parse(hospitalRaw.getWednesdayStart()),
					OpeningHourParser.parse(hospitalRaw.getWednesdayClose())));
			}

			if (Objects.nonNull(hospitalRaw.getThursdayStart()) && Objects.nonNull(hospitalRaw.getThursdayClose())) {
				days.add(Day.of(Day.DayType.THURSDAY,
					OpeningHourParser.parse(hospitalRaw.getThursdayStart()),
					OpeningHourParser.parse(hospitalRaw.getThursdayClose())));
			}

			if (Objects.nonNull(hospitalRaw.getFridayStart()) && Objects.nonNull(hospitalRaw.getFridayClose())) {
				days.add(Day.of(Day.DayType.FRIDAY,
					OpeningHourParser.parse(hospitalRaw.getFridayStart()),
					OpeningHourParser.parse(hospitalRaw.getFridayClose())));
			}

			if (Objects.nonNull(hospitalRaw.getSaturdayStart()) && Objects.nonNull(hospitalRaw.getSaturdayClose())) {
				days.add(Day.of(Day.DayType.SATURDAY,
					OpeningHourParser.parse(hospitalRaw.getSaturdayStart()),
					OpeningHourParser.parse(hospitalRaw.getSaturdayClose())));
			}

			if (Objects.nonNull(hospitalRaw.getSundayStart()) && Objects.nonNull(hospitalRaw.getSundayClose())) {
				days.add(Day.of(Day.DayType.SUNDAY,
					OpeningHourParser.parse(hospitalRaw.getSundayStart()),
					OpeningHourParser.parse(hospitalRaw.getSundayClose())));
			}

			if (Objects.nonNull(hospitalRaw.getHolidayStart()) && Objects.nonNull(hospitalRaw.getHolidayClose())) {
				days.add(Day.of(Day.DayType.HOLIDAY,
					OpeningHourParser.parse(hospitalRaw.getHolidayStart()),
					OpeningHourParser.parse(hospitalRaw.getHolidayClose())));
			}

			return Hospital.builder()
				.id(hospitalRaw.getId())
				.name(hospitalRaw.getName())
				.location(new GeoJsonPoint(hospitalRaw.getLongitude(), hospitalRaw.getLatitude()))
				.phone(hospitalRaw.getPhone())
				.address(hospitalRaw.getAddress())
				.hospitalType(HospitalType.find(hospitalRaw.getType()))
				.days(days)
				.build();
		} catch (Exception e) {
			log.error("[FAIL TO MIGRATION] {} {}", hospitalRaw.getId(), e.getMessage());
			return null;
		}
	}
}