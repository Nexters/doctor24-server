package me.nexters.doctor24.medical;

import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import reactor.core.publisher.Flux;

/**
 * @author manki.kim
 */
public interface MedicalAggregator {

	MedicalType type();

	Flux<FacilityResponse> getFacilitiesFilteringByDay(double latitude, double longitude,
		Day requestDay);

	//요거는 hospital type만 있기 때문에 인터페이스를 분리하는게 나을 듯... 귀찮으니 나중에 넥나잇에서 정리해야겠다
	Flux<FacilityResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude,
		String category, Day requestDay);

	Flux<FacilityResponse> getFacilitiesWithIn(double xlatitude, double xlongitude, double zlatitude,
		double zlongitude, Day requestDay);
}
