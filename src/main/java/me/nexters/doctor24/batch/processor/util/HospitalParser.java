package me.nexters.doctor24.batch.processor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.experimental.UtilityClass;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.HospitalType;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@UtilityClass
public class HospitalParser {
	public static Hospital parse(HospitalRaw hospitalRaw) {
		List<Day> days = extractDays(hospitalRaw);
		return Hospital.builder()
			.id(hospitalRaw.getId())
			.name(hospitalRaw.getName())
			.location(new GeoJsonPoint(hospitalRaw.getLongitude(), hospitalRaw.getLatitude()))
			.phone(hospitalRaw.getPhone())
			.address(AddressParser.parse(hospitalRaw.getAddress()))
			.hospitalType(HospitalType.find(hospitalRaw.getType()))
			.days(days)
			.build();

	}

	private static List<Day> extractDays(HospitalRaw hospitalRaw) {
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

		return days;
	}

}
