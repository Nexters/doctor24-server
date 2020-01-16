package me.nexters.doctor24.medical.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalService {

	private static final double DEFAULT_DISTANCE = 0.5;
	private static final int DEFAULT_PAGE_COUNT = 15;
	private static final int PAGE_COUNT_WITH_FILTERING = 30;

	private final HospitalRepository hospitalRepository;

	public Flux<FacilityResponse> getFacilitiesWithinRange(double latitude, double longitude) {
		return getHospitalFlux(latitude, longitude, DEFAULT_PAGE_COUNT)
			.map(FacilityResponse::from);
	}

	// 시간 필터링은 복잡하기 때문에 앱에서 하지만 진료 과목 같은 경우는 mongo in 쿼리를 활용해서 몽고에서 처리 하도록 해야할듯
	// 앱에서 전부 필터링을 처리 하기에는 적정 개수를 맞춰주기 어려움
	public Flux<FacilityResponse> getFacilitiesWithinRangeWithFiltering(double latitude, double longitude,
		Day requestDay) {
		return getHospitalFlux(latitude, longitude, PAGE_COUNT_WITH_FILTERING)
			.filter(hospital -> hospital.isOpen(requestDay))
			.map(FacilityResponse::from);
	}

	private Flux<Hospital> getHospitalFlux(double latitude, double longitude, int requestCount) {
		return hospitalRepository.findByLocationNear(new Point(longitude, latitude),
			new Distance(DEFAULT_DISTANCE, Metrics.KILOMETERS),
			PageRequest.of(0, requestCount, Sort.by(Sort.Direction.ASC, "location")));
	}
}
