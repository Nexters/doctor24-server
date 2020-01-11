package me.nexters.doctor24.medical.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalService {
	private final HospitalRepository hospitalRepository;

	public Flux<FacilityResponse> getFacilitiesWithinRange(String latitude, String longitude) {
		return null;
	}
}
