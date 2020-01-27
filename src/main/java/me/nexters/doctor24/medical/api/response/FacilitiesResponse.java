package me.nexters.doctor24.medical.api.response;

import java.util.List;

import org.springframework.util.CollectionUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author manki.kim
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacilitiesResponse {
	@Schema(description = "위도")
	private double latitude;

	@Schema(description = "경도")
	private double longitude;

	@Schema(description = "의료 기관 리스트")
	private List<FacilityResponse> facilities;

	@Schema(description = "기관 리스트 카운트 정보")
	private int total;

	public static FacilitiesResponse of(List<FacilityResponse> facilityResponses) {
		if (CollectionUtils.isEmpty(facilityResponses)) {
			return new FacilitiesResponse();
		}
		FacilityResponse first = facilityResponses.get(0);
		return new FacilitiesResponse(first.getLatitude(),
			first.getLongitude(), facilityResponses, facilityResponses.size());
	}
}
