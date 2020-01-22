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
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyRepository;
import reactor.core.publisher.Flux;

/**
 * @author manki.kim
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PharmacyAggregator implements MedicalAggregator {

	private static final double DEFAULT_DISTANCE = 0.5;
	private static final int PAGE_COUNT_WITH_FILTERING = 30;

	private final PharmacyRepository pharmacyRepository;

	@Override
	public MedicalType type() {
		return MedicalType.pharmacy;
	}

	@Override
	public Flux<FacilityResponse> getFacilitiesFilteringByDay(double latitude, double longitude, Day requestDay) {
		return pharmacyRepository.findByLocationNear(new Point(longitude, latitude),
			new Distance(DEFAULT_DISTANCE, Metrics.KILOMETERS),
			PageRequest.of(0, PAGE_COUNT_WITH_FILTERING, Sort.by(Sort.Direction.ASC, "location")))
			.map(FacilityResponse::fromPharmacy);
	}

	@Override
	public Flux<FacilityResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude,
		String category, Day requestDay) {
		throw new UnsupportedOperationException("not support category filtering about pharmacy type");
	}
}
