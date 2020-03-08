package me.nexters.doctor24.medical.external.holiday.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

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
