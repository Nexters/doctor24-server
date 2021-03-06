package me.nexters.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;

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

	public boolean isInRange(LocalTime startTime, LocalTime endTime) {
		return this.startTime.compareTo(startTime) <= 0
			&& this.endTime.compareTo(endTime) >= 0;
	}

	public boolean isEqual(Day day) {
		return this.dayType == day.dayType;
	}
}

