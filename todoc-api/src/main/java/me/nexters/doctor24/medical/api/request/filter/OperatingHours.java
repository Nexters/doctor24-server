package me.nexters.doctor24.medical.api.request.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;

/**
 * @author manki.kim
 */
@Data
public class OperatingHours {
	@Schema(description = "기관 오픈 시간", example = "10:00:00")
	@NotEmpty
	private String startTime;
	@Schema(description = "기관 종료 시간", example = "21:00:00")
	@NotEmpty
	private String endTime;

	public LocalTime startTimeToLocalTime() {
		return LocalTime.parse(startTime);
	}

	public LocalTime endTimeToLocalTime() {
		return LocalTime.parse(endTime);
	}
}
