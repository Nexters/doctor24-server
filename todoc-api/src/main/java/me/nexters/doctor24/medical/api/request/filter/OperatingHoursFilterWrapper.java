package me.nexters.doctor24.medical.api.request.filter;

import lombok.Data;
import me.nexters.doctor24.medical.external.holiday.HolidayManager;
import me.nexters.domain.common.Day;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * @author manki.kim
 */
@Data
public class OperatingHoursFilterWrapper {
	@Valid
	private OperatingHours operatingHours;

	public Day getDay(HolidayManager holidayManager) {
		Day requestDay = operatingHours == null ? currentTimeDay() : toDay();
		Day validatedRequestDay = validateDayHours(requestDay);
		if (!holidayManager.isHoliday(LocalDate.now())) {
			return validatedRequestDay;
		}
		return Day.of(Day.DayType.HOLIDAY, validatedRequestDay.getStartTime(), validatedRequestDay.getEndTime());
	}

	private Day validateDayHours(Day requestDay) {
		if (requestDay.getEndTime().getHour() == 0) {
			return Day.of(requestDay.getDayType(), requestDay.getStartTime(), LocalTime.of(23, 59));
		}

		if (requestDay.getStartTime().getHour() > requestDay.getEndTime().getHour()) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,
				"startTime must be less than end time  " + requestDay);
		}

		return requestDay;
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

