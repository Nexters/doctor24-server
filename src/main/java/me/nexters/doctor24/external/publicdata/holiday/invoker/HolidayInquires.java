package me.nexters.doctor24.external.publicdata.holiday.invoker;

import java.util.List;

import me.nexters.doctor24.external.publicdata.holiday.model.HolidayRaw;

/**
 * @author manki.kim
 */
public interface HolidayInquires {
	List<HolidayRaw> getHolidayRaws(int year);
}
