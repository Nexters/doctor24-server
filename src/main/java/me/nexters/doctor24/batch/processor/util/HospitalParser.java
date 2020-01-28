package me.nexters.doctor24.batch.processor.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.experimental.UtilityClass;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalType;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@UtilityClass
public class HospitalParser {
	public static Hospital parse(HospitalBasicRaw hospitalBasicRaw) {
		List<Day> days = extractDays(hospitalBasicRaw);
		return Hospital.builder()
			.id(hospitalBasicRaw.getId())
			.name(hospitalBasicRaw.getName())
			.location(new GeoJsonPoint(hospitalBasicRaw.getLongitude(), hospitalBasicRaw.getLatitude()))
			.phone(hospitalBasicRaw.getPhone())
			.address(AddressParser.parse(hospitalBasicRaw.getAddress()))
			.hospitalType(HospitalType.find(hospitalBasicRaw.getType()))
			.days(days)
			.isEmergency(EmergencyParser.parse(hospitalBasicRaw.getEmergencyCode()))
			.rowWriteDate(LocalDateTime.now())
			.build();
	}

	private static List<Day> extractDays(HospitalBasicRaw hospitalBasicRaw) {
		List<Day> days = new ArrayList<>();
		if (Objects.nonNull(hospitalBasicRaw.getMondayStart()) && Objects.nonNull(hospitalBasicRaw.getMondayClose())) {
			days.add(Day.of(Day.DayType.MONDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getMondayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getMondayClose())));
		}

		if (Objects.nonNull(hospitalBasicRaw.getTuesdayStart()) && Objects.nonNull(
			hospitalBasicRaw.getTuesdayClose())) {
			days.add(Day.of(Day.DayType.TUESDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getTuesdayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getTuesdayClose())));
		}

		if (Objects.nonNull(hospitalBasicRaw.getWednesdayStart()) && Objects.nonNull(
			hospitalBasicRaw.getWednesdayClose())) {
			days.add(Day.of(Day.DayType.WEDNESDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getWednesdayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getWednesdayClose())));
		}

		if (Objects.nonNull(hospitalBasicRaw.getThursdayStart()) && Objects.nonNull(
			hospitalBasicRaw.getThursdayClose())) {
			days.add(Day.of(Day.DayType.THURSDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getThursdayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getThursdayClose())));
		}

		if (Objects.nonNull(hospitalBasicRaw.getFridayStart()) && Objects.nonNull(hospitalBasicRaw.getFridayClose())) {
			days.add(Day.of(Day.DayType.FRIDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getFridayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getFridayClose())));
		}

		if (Objects.nonNull(hospitalBasicRaw.getSaturdayStart()) && Objects.nonNull(
			hospitalBasicRaw.getSaturdayClose())) {
			days.add(Day.of(Day.DayType.SATURDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getSaturdayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getSaturdayClose())));
		}

		if (Objects.nonNull(hospitalBasicRaw.getSundayStart()) && Objects.nonNull(hospitalBasicRaw.getSundayClose())) {
			days.add(Day.of(Day.DayType.SUNDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getSundayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getSundayClose())));
		}

		if (Objects.nonNull(hospitalBasicRaw.getHolidayStart()) && Objects.nonNull(
			hospitalBasicRaw.getHolidayClose())) {
			days.add(Day.of(Day.DayType.HOLIDAY,
				OpeningHourParser.parse(hospitalBasicRaw.getHolidayStart()),
				OpeningHourParser.parse(hospitalBasicRaw.getHolidayClose())));
		}

		return days;
	}
}
