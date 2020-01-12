package me.nexters.doctor24.medical.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalService {

	private static final double DEFAULT_DISTANCE = 0.5;

	private final HospitalRepository hospitalRepository;

	public Flux<FacilityResponse> getFacilitiesWithinRange(double latitude, double longitude) {
		return hospitalRepository.findByLocationNear(new Point(longitude, latitude),
			new Distance(DEFAULT_DISTANCE, Metrics.KILOMETERS))
			.map(FacilityResponse::from);
	}
}
