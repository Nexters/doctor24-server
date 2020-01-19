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
}
