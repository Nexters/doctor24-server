package me.nexters.doctor24.medical.external.holiday;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

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
