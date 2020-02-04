package me.nexters.doctor24.medical.pharmacy.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.MedicalAggregator;
import me.nexters.doctor24.medical.api.response.FacilityIndexResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PharmacyAggregator implements MedicalAggregator {

	private static final String LOCATION_FILED = "location";

	private final PharmacyRepository pharmacyRepository;

	@Override
	public MedicalType type() {
		return MedicalType.pharmacy;
	}

	@Override
	public Mono<FacilityResponse> getFacilityBy(String facilityId) {
		return pharmacyRepository.findById(facilityId)
			.map(FacilityResponse::fromPharmacy);
	}

	@Override
	public Flux<FacilityIndexResponse> getFacilitiesFilteringByDay(double latitude, double longitude,
		double radiusRange, int inquiryCount, Day requestDay) {
		return pharmacyRepository.findByLocationNear(new Point(longitude, latitude),
			new Distance(radiusRange, Metrics.KILOMETERS),
			PageRequest.of(0, inquiryCount, Sort.by(Sort.Direction.ASC, LOCATION_FILED)))
			.filter(pharmacy -> pharmacy.isOpen(requestDay))
			.map(pharmacy -> FacilityIndexResponse.fromPharmacy(pharmacy, requestDay));
	}

	@Override
	public Flux<FacilityIndexResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude,
		double radiusRange, int inquiryCount, String category, Day requestDay) {
		throw new UnsupportedOperationException("not support category filtering about pharmacy type");
	}
}
