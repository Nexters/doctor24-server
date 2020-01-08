package me.nexters.doctor24.medical.api.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import me.nexters.doctor24.medical.common.Day;

/**
 * @author manki.kim
 */
@Builder
@Value(staticConstructor = "of")
public class FacilityResponse {
	@Schema(description = "지점 이름")
	private String name;

	@Schema(description = "위도")
	private double latitude;

	@Schema(description = "경도")
	private double longitude;

	@Schema(description = "의료 서비스 카테고리")
	private String medicalType;

	@Schema(description = "운영시간")
	private List<Day> days;

	@Schema(description = "전화번호")
	private String phone;

	@Schema(description = "주소")
	private String address;
}
