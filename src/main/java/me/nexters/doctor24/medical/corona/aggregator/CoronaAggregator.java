package me.nexters.doctor24.medical.corona.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.MedicalAggregator;
import me.nexters.doctor24.medical.api.response.FacilityIndexResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.corona.repository.CoronaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoronaAggregator implements MedicalAggregator {

	private final CoronaRepository coronaRepository;

	@Override
	public MedicalType type() {
		return MedicalType.corona;
	}

	@Override
	public Mono<FacilityResponse> getFacilityBy(String facilityId) {
		return coronaRepository.findById(facilityId)
			.map(FacilityResponse::fromCoronaHospital);
	}

	@Override
	public Flux<FacilityIndexResponse> getFacilitiesFilteringByDay(double latitude, double longitude,
		double radiusRange, int inquiryCount, Day requestDay) {
		return coronaRepository.findByLocationNear(new Point(longitude, latitude),
			new Distance(10.0, Metrics.KILOMETERS),
			PageRequest.of(0, inquiryCount))
			.filter(hospital -> hospital.isOpen(requestDay))
			.map(hospital -> FacilityIndexResponse.fromCoronaHospital(hospital, requestDay));
	}

	@Override
	public Flux<FacilityIndexResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude,
		double radiusRange, int inquiryCount, String category, Day requestDay) {
		throw new UnsupportedOperationException("not support category filtering about corona type");
	}
}
