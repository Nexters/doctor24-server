package me.nexters.doctor24.medical.aggregator.hospital;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.aggregator.MedicalAggregator;
import me.nexters.doctor24.medical.api.response.FacilityIndexResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.domain.common.Day;
import me.nexters.domain.hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalAggregator implements MedicalAggregator {

	private final HospitalRepository hospitalRepository;

	@Override
	public MedicalType type() {
		return MedicalType.hospital;
	}

	@Override
	public Mono<FacilityResponse> getFacilityBy(String facilityId) {
		return hospitalRepository.findById(facilityId)
			.map(FacilityResponse::fromHospital);
	}

	@Override
	public Flux<FacilityIndexResponse> getFacilitiesFilteringByDay(double latitude, double longitude,
		double radiusRange, int inquiryCount, Day requestDay) {
		return hospitalRepository.findByLocationNear(new Point(longitude, latitude),
			new Distance(radiusRange, Metrics.KILOMETERS),
			PageRequest.of(0, inquiryCount))
			.filter(hospital -> hospital.isOpen(requestDay))
			.map(hospital -> FacilityIndexResponse.fromHospital(hospital, requestDay));
	}

	@Override
	public Flux<FacilityIndexResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude,
		double radiusRange, int inquiryCount, String category, Day requestDay) {
		return hospitalRepository.findByLocationNearAndCategories(new Point(longitude, latitude),
			new Distance(radiusRange, Metrics.KILOMETERS), category,
			PageRequest.of(0, inquiryCount))
			.filter(hospital -> hospital.isOpen(requestDay))
			.map(hospital -> FacilityIndexResponse.fromHospital(hospital, requestDay));
	}
}
