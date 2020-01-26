package me.nexters.doctor24.medical.hospital.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.MedicalAggregator;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalAggregator implements MedicalAggregator {

	private static final double DEFAULT_DISTANCE = 0.5;
	private static final int PAGE_COUNT_WITH_FILTERING = 50;
	private static final String LOCATION_FILED = "location";

	private final HospitalRepository hospitalRepository;

	@Override
	public MedicalType type() {
		return MedicalType.hospital;
	}

	@Override
	public Flux<FacilityResponse> getFacilitiesFilteringByDay(double latitude, double longitude, Day requestDay) {
		return hospitalRepository.findByLocationNear(new Point(longitude, latitude),
			new Distance(DEFAULT_DISTANCE, Metrics.KILOMETERS),
			PageRequest.of(0, PAGE_COUNT_WITH_FILTERING, Sort.by(Sort.Direction.ASC, LOCATION_FILED)))
			.filter(hospital -> hospital.isOpen(requestDay))
			.map(FacilityResponse::fromHospital);
	}

	@Override
	public Flux<FacilityResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude,
		String category, Day requestDay) {
		return hospitalRepository.findByLocationNearAndCategories(new Point(longitude, latitude),
			new Distance(DEFAULT_DISTANCE, Metrics.KILOMETERS), category,
			PageRequest.of(0, PAGE_COUNT_WITH_FILTERING, Sort.by(Sort.Direction.ASC, LOCATION_FILED)))
			.filter(hospital -> hospital.isOpen(requestDay))
			.map(FacilityResponse::fromHospital);
	}

	@Override
	public Flux<FacilityResponse> getFacilitiesWithIn(Polygon polygon, Day requestDay) {
		return hospitalRepository
			.findByLocationWithin(
				polygon, PageRequest.of(0, PAGE_COUNT_WITH_FILTERING, Sort.by(Sort.Direction.ASC, LOCATION_FILED)))
			.filter(hospital -> hospital.isOpen(requestDay))
			.map(FacilityResponse::fromHospital);
	}
}
