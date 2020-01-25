package me.nexters.doctor24.medical.api.request.filter;

import java.time.LocalDateTime;
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

	public Day getDay() {
		return operatingHours == null ? currentTimeDay() : toDay();
	}

	private Day toDay() {
		try {
			return Day.of(operatingHours.getDay(), operatingHours.startTimeToLocalTime(),
				operatingHours.endTimeToLocalTime());
		} catch (DateTimeParseException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "date time parsing error " + operatingHours);
		}
	}

	private Day currentTimeDay() {
		LocalDateTime now = LocalDateTime.now();
		return Day.of(Day.DayType.valueOf(now.getDayOfWeek().name()),
			now.toLocalTime(), now.toLocalTime());
	}
}

