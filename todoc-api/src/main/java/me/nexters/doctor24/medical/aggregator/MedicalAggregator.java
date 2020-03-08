package me.nexters.doctor24.medical.aggregator;

import me.nexters.doctor24.medical.api.response.FacilityIndexResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.domain.common.Day;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
public interface MedicalAggregator {

	MedicalType type();

	Mono<FacilityResponse> getFacilityBy(String facilityId);

	Flux<FacilityIndexResponse> getFacilitiesFilteringByDay(double latitude, double longitude,
                                                            double radiusRange, int inquiryCount, Day requestDay);

	//요거는 hospital type만 있기 때문에 인터페이스를 분리하는게 나을 듯... 귀찮으니 나중에 넥나잇에서 정리해야겠다
	Flux<FacilityIndexResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude,
                                                                       double radiusRange, int inquiryCount, String category, Day requestDay);
}
