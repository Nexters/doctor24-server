package me.nexters.doctor24.batch.config.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@UtilityClass
public class JobParameterUtil {
	public static LocalDateTime millsToLocalDateTime(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
