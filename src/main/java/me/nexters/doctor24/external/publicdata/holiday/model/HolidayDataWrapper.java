package me.nexters.doctor24.external.publicdata.holiday.model;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

/**
 * @author manki.kim
 */
@Getter
@ToString
public class HolidayDataWrapper {
	private List<HolidayRaw> item;
}
