package me.nexters.doctor24.medical.external.holiday;

import java.util.List;

import me.nexters.doctor24.medical.external.holiday.dto.HolidayRaw;

/**
 * @author manki.kim
 */
public interface HolidayInquires {
    List<HolidayRaw> getHolidayRaws(int year, int month);
}
