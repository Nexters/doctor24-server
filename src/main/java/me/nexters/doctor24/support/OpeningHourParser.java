package me.nexters.doctor24.support;

import java.time.LocalTime;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpeningHourParser {
	public static LocalTime parse(String input) {
		int hour = Integer.parseInt(input.substring(0, 2));
		if (hour >= 24) {
			return LocalTime.of(23, 59);
		}
		int min = Integer.parseInt(input.substring(2, 4));
		return LocalTime.of(hour, min);
	}
}
