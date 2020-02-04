package me.nexters.doctor24.medical.api.request.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import lombok.Data;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.holiday.HolidayManager;

/**
 * @author manki.kim
 */
@Data
public class OperatingHoursFilterWrapper {
	@Valid
	private OperatingHours operatingHours;

	public Day getDay(HolidayManager holidayManager) {
		Day requestDay = operatingHours == null ? currentTimeDay() : toDay();
		if (!holidayManager.isHoliday(LocalDate.now())) {
			return requestDay;
		}
		return Day.of(Day.DayType.HOLIDAY, requestDay.getStartTime(), requestDay.getEndTime());
	}

	private Day toDay() {
		try {
			return Day.of(Day.DayType.valueOf(LocalDate.now().getDayOfWeek().name()),
				operatingHours.startTimeToLocalTime(),
				operatingHours.endTimeToLocalTime());
		} catch (DateTimeParseException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "date time parsing error " + operatingHours);
		}
	}

	private Day currentTimeDay() {
		LocalDateTime now = LocalDateTime.now();
		return Day.of(Day.DayType.valueOf(now.getDayOfWeek().name()),
			now.toLocalTime(), now.toLocalTime().plusHours(1));
	}
}

