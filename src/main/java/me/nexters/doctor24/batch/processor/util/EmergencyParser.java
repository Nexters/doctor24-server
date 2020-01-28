package me.nexters.doctor24.batch.processor.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmergencyParser {
	private static final String EMERGENCY_CODE = "1";

	public static boolean parse(String input) {
		return input.equals(EMERGENCY_CODE);
	}
}
