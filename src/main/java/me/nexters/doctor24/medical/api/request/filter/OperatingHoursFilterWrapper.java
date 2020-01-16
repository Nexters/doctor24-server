package me.nexters.doctor24.medical.api.request.filter;

import java.time.format.DateTimeParseException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import lombok.Data;
import me.nexters.doctor24.medical.common.Day;

/**
 * @author manki.kim
 */
@Data
public class OperatingHoursFilterWrapper {
	@Valid
	private OperatingHours operatingHours;

	public Day toDay() {
		try {
			return Day.of(operatingHours.getDay(), operatingHours.startTimeToLocalTime(),
				operatingHours.endTimeToLocalTime());
		} catch (DateTimeParseException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "date time parsing error " + operatingHours);
		}
	}
}

