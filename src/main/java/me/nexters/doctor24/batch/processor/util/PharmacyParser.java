package me.nexters.doctor24.batch.processor.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

@Slf4j
@UtilityClass
public class PharmacyParser {
	public static Pharmacy parse(PharmacyRaw pharmacyRaw) {
		List<Day> days = extractDays(pharmacyRaw);
		return Pharmacy.builder()
			.id(pharmacyRaw.getId())
			.days(days)
			.name(pharmacyRaw.getName())
			.location(new GeoJsonPoint(pharmacyRaw.getLongitude(), pharmacyRaw.getLatitude()))
			.address(pharmacyRaw.getAddress())
			.phone(pharmacyRaw.getPhone())
			.rowWriteDate(LocalDateTime.now())
			.build();
	}

	private static List<Day> extractDays(PharmacyRaw pharmacyRaw) {
		List<Day> days = new ArrayList<>();
		if (Objects.nonNull(pharmacyRaw.getMondayStart()) && Objects.nonNull(pharmacyRaw.getMondayClose())) {
			days.add(Day.of(Day.DayType.MONDAY,
				OpeningHourParser.parse(pharmacyRaw.getMondayStart()),
				OpeningHourParser.parse(pharmacyRaw.getMondayClose())));
		}

		if (Objects.nonNull(pharmacyRaw.getTuesdayStart()) && Objects.nonNull(
			pharmacyRaw.getTuesdayClose())) {
			days.add(Day.of(Day.DayType.TUESDAY,
				OpeningHourParser.parse(pharmacyRaw.getTuesdayStart()),
				OpeningHourParser.parse(pharmacyRaw.getTuesdayClose())));
		}

		if (Objects.nonNull(pharmacyRaw.getWednesdayStart()) && Objects.nonNull(
			pharmacyRaw.getWednesdayClose())) {
			days.add(Day.of(Day.DayType.WEDNESDAY,
				OpeningHourParser.parse(pharmacyRaw.getWednesdayStart()),
				OpeningHourParser.parse(pharmacyRaw.getWednesdayClose())));
		}

		if (Objects.nonNull(pharmacyRaw.getThursdayStart()) && Objects.nonNull(
			pharmacyRaw.getThursdayClose())) {
			days.add(Day.of(Day.DayType.THURSDAY,
				OpeningHourParser.parse(pharmacyRaw.getThursdayStart()),
				OpeningHourParser.parse(pharmacyRaw.getThursdayClose())));
		}

		if (Objects.nonNull(pharmacyRaw.getFridayStart()) && Objects.nonNull(pharmacyRaw.getFridayClose())) {
			days.add(Day.of(Day.DayType.FRIDAY,
				OpeningHourParser.parse(pharmacyRaw.getFridayStart()),
				OpeningHourParser.parse(pharmacyRaw.getFridayClose())));
		}

		if (Objects.nonNull(pharmacyRaw.getSaturdayStart()) && Objects.nonNull(
			pharmacyRaw.getSaturdayClose())) {
			days.add(Day.of(Day.DayType.SATURDAY,
				OpeningHourParser.parse(pharmacyRaw.getSaturdayStart()),
				OpeningHourParser.parse(pharmacyRaw.getSaturdayClose())));
		}

		if (Objects.nonNull(pharmacyRaw.getSundayStart()) && Objects.nonNull(pharmacyRaw.getSundayClose())) {
			days.add(Day.of(Day.DayType.SUNDAY,
				OpeningHourParser.parse(pharmacyRaw.getSundayStart()),
				OpeningHourParser.parse(pharmacyRaw.getSundayClose())));
		}

		if (Objects.nonNull(pharmacyRaw.getHolidayStart()) && Objects.nonNull(
			pharmacyRaw.getHolidayClose())) {
			days.add(Day.of(Day.DayType.HOLIDAY,
				OpeningHourParser.parse(pharmacyRaw.getHolidayStart()),
				OpeningHourParser.parse(pharmacyRaw.getHolidayClose())));
		}

		return days;
	}

}
