package me.nexters.doctor24.medical.common;

import java.time.LocalTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "of")
public class Day {
	private final DayType dayType;
	private final LocalTime startTime;
	private final LocalTime endTime;

	public enum DayType {
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY,
		HOLIDAY;
	}
}

