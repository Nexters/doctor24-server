package me.nexters.doctor24.medical.common;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class Day {
	private final DayOfWeek dayOfWeek;
	private final LocalTime startTime;
	private final LocalTime endTime;
}

