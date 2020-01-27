package me.nexters.doctor24.external.publicdata.holiday.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author manki.kim
 */
@Getter
@ToString
public class HolidayRaw {
	private int dateKind;
	private String dateName;
	private String isHoliday;
	@JsonProperty("locdate")
	private int date;
}
