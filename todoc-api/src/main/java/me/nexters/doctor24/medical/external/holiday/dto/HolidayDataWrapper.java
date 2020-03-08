package me.nexters.doctor24.medical.external.holiday.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author manki.kim
 */
@Getter
@ToString
public class HolidayDataWrapper {
    private List<HolidayRaw> item;
}
