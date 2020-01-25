package me.nexters.doctor24.batch.processor.util;

import java.time.LocalTime;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpeningHourParser {
	public static final int VALID_HOUR_LENGTH = 4;

	public static LocalTime parse(String input) {
		if (checkInput(input)) {
			return LocalTime.of(0, 0);
		}
		int hour = Integer.parseInt(input.substring(0, 2));
		if (hour >= 24) {
			return LocalTime.of(23, 59);
		}
		int min = Integer.parseInt(input.substring(2, 4));
		return LocalTime.of(hour, min);
	}

	private static boolean checkInput(String input) {
		return input.length() != VALID_HOUR_LENGTH;
	}
}
