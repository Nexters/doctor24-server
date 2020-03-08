package me.nexters.doctor24.medical.external.holiday;

import me.nexters.doctor24.medical.external.holiday.dto.HolidayRaw;

import java.util.List;

/**
 * @author manki.kim
 */
public interface HolidayInquires {
    List<HolidayRaw> getHolidayRaws(int year);
}
