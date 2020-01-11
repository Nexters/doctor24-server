package me.nexters.doctor24.medical.hospital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.api.request.param.Location;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;
import me.nexters.doctor24.support.DistanceUtils;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalService {
	private final HospitalRepository hospitalRepository;

	// TODO 조회 로직 개선 해야 함
	public List<FacilityResponse> getHospitalsWithin(Location location) {
		return hospitalRepository.findAll().stream()
			.filter(
				hospital -> DistanceUtils.calculateDistanceInKilometer(hospital.getLatitude(), hospital.getLongitude(),
					Double.parseDouble(location.getLatitude()), Double.parseDouble(location.getLongitude())) <=
					location.getRadiusKilometer())
			.map(this::toFacilitiyResponse)
			.collect(Collectors.toList());
	}

	private FacilityResponse toFacilitiyResponse(Hospital hospital) {
		return FacilityResponse.builder()
			.medicalType("치과") // TODO 카테고리화
			.name(hospital.getName())
			.address(hospital.getAddress())
			.days(hospital.getDays())
			.latitude(hospital.getLatitude())
			.longitude(hospital.getLongitude())
			.phone(hospital.getPhone())
			.build();
	}

	public Flux<FacilityResponse> getFacilitiesWithinRange(String latitude, String longitude) {
		return null;
	}
}
