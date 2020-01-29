package me.nexters.doctor24.batch.processor.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import lombok.experimental.UtilityClass;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalType;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@UtilityClass
public class HospitalParser {
	public static Hospital parse(HospitalBasicRaw hospitalBasicRaw, HospitalDetailRaw hospitalDetailRaw) {
		List<Day> days = extractDays(hospitalDetailRaw);
		boolean isEmergency = extractEmergency(hospitalBasicRaw);

		return Hospital.builder()
			.id(hospitalBasicRaw.getId())
			.name(hospitalBasicRaw.getName())
			.location(new GeoJsonPoint(hospitalBasicRaw.getLongitude(), hospitalBasicRaw.getLatitude()))
			.phone(hospitalBasicRaw.getPhone())
			.address(AddressParser.parse(hospitalBasicRaw.getAddress()))
			.hospitalType(HospitalType.find(hospitalBasicRaw.getType()))
			.days(days)
			.categories(CategoryParser.parse(hospitalDetailRaw.getCategories()))
			.isEmergency(isEmergency)
			.rowWriteDate(LocalDateTime.now())
			.build();
	}

	public static Hospital parse(HospitalDetailRaw hospitalDetailRaw) {
		List<Day> days = extractDays(hospitalDetailRaw);
		boolean isEmergency = extractEmergency(hospitalDetailRaw);

		return Hospital.builder()
			.id(hospitalDetailRaw.getId())
			.name(hospitalDetailRaw.getName())
			.location(new GeoJsonPoint(hospitalDetailRaw.getLongitude(), hospitalDetailRaw.getLatitude()))
			.phone(hospitalDetailRaw.getPhone())
			.address(AddressParser.parse(hospitalDetailRaw.getAddress()))
			.hospitalType(HospitalType.find(hospitalDetailRaw.getType()))
			.days(days)
			.categories(CategoryParser.parse(hospitalDetailRaw.getCategories()))
			.isEmergency(isEmergency)
			.rowWriteDate(LocalDateTime.now())
			.build();
	}

	// 추상화로 묶을까..
	private static boolean extractEmergency(HospitalBasicRaw hospitalBasicRaw) {
		return Optional.of(EmergencyParser.parse(hospitalBasicRaw.getEmergencyCode())).orElse(false);
	}

	private static boolean extractEmergency(HospitalDetailRaw hospitalDetailRaw) {
		return Optional.of(EmergencyParser.parse(hospitalDetailRaw.getEmergencyCode())).orElse(false);
	}

	private static List<Day> extractDays(HospitalDetailRaw hospitalDetailRaw) {
		List<Day> days = new ArrayList<>();
		if (Objects.nonNull(hospitalDetailRaw.getMondayStart()) && Objects.nonNull(
			hospitalDetailRaw.getMondayClose())) {
			days.add(Day.of(Day.DayType.MONDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getMondayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getMondayClose())));
		}

		if (Objects.nonNull(hospitalDetailRaw.getTuesdayStart()) && Objects.nonNull(
			hospitalDetailRaw.getTuesdayClose())) {
			days.add(Day.of(Day.DayType.TUESDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getTuesdayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getTuesdayClose())));
		}

		if (Objects.nonNull(hospitalDetailRaw.getWednesdayStart()) && Objects.nonNull(
			hospitalDetailRaw.getWednesdayClose())) {
			days.add(Day.of(Day.DayType.WEDNESDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getWednesdayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getWednesdayClose())));
		}

		if (Objects.nonNull(hospitalDetailRaw.getThursdayStart()) && Objects.nonNull(
			hospitalDetailRaw.getThursdayClose())) {
			days.add(Day.of(Day.DayType.THURSDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getThursdayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getThursdayClose())));
		}

		if (Objects.nonNull(hospitalDetailRaw.getFridayStart()) && Objects.nonNull(
			hospitalDetailRaw.getFridayClose())) {
			days.add(Day.of(Day.DayType.FRIDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getFridayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getFridayClose())));
		}

		if (Objects.nonNull(hospitalDetailRaw.getSaturdayStart()) && Objects.nonNull(
			hospitalDetailRaw.getSaturdayClose())) {
			days.add(Day.of(Day.DayType.SATURDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getSaturdayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getSaturdayClose())));
		}

		if (Objects.nonNull(hospitalDetailRaw.getSundayStart()) && Objects.nonNull(
			hospitalDetailRaw.getSundayClose())) {
			days.add(Day.of(Day.DayType.SUNDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getSundayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getSundayClose())));
		}

		if (Objects.nonNull(hospitalDetailRaw.getHolidayStart()) && Objects.nonNull(
			hospitalDetailRaw.getHolidayClose())) {
			days.add(Day.of(Day.DayType.HOLIDAY,
				OpeningHourParser.parse(hospitalDetailRaw.getHolidayStart()),
				OpeningHourParser.parse(hospitalDetailRaw.getHolidayClose())));
		}

		return days;
	}
}
