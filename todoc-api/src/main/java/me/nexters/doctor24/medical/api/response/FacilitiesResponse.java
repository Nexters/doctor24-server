package me.nexters.doctor24.medical.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
	private List<FacilityIndexResponse> facilities;

	@Schema(description = "기관 리스트 카운트 정보")
	private int total;

	public static FacilitiesResponse of(List<FacilityIndexResponse> facilityResponses) {
		if (CollectionUtils.isEmpty(facilityResponses)) {
			return new FacilitiesResponse();
		}
		FacilityIndexResponse first = facilityResponses.get(0);
		return new FacilitiesResponse(first.getLatitude(),
			first.getLongitude(), facilityResponses, facilityResponses.size());
	}
}
