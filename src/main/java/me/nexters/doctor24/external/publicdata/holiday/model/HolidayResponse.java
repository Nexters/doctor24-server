package me.nexters.doctor24.external.publicdata.holiday.model;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

/**
 * @author manki.kim
 */
@Getter
@ToString
public class HolidayResponse {
	private HolidayBody body;

	public List<HolidayRaw> getHolidayRaws() {
		return body.getItems().getItem();
	}
}
