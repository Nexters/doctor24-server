package me.nexters.doctor24.medical.holiday;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;

/**
 * @author manki.kim
 */
@AllArgsConstructor
public class HolidayManager {
	private final Set<LocalDate> holidaySet;

	public boolean isHoliday(LocalDate localDate) {
		return holidaySet.contains(localDate);
	}
}
