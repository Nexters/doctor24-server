package me.nexters.doctor24.medical.api.request.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author manki.kim
 */
@Data
public class Location {
	@NotNull
	@Schema(description = "위도 값")
	private String latitude;

	@NotNull
	@Schema(description = "경도 값")
	private String longitude;

	@Schema(description = "반경 범위")
	private int radiusKilometer = 1;
}
