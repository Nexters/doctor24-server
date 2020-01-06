package me.nexters.doctor24.medical.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

/**
 * @author manki.kim
 */
@Value(staticConstructor = "of")
public class FacilityResponse {
	@Schema(description = "의료 서비스 카테고리")
	private String medicalType;
	@Schema(description = "지점 이름")
	private String facilityName;
}
