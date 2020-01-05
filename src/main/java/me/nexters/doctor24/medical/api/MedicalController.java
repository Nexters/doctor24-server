package me.nexters.doctor24.medical.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.nexters.doctor24.medical.api.request.param.Location;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import reactor.core.publisher.Flux;

/**
 * @author manki.kim
 */
@RestController
@RequestMapping("/api/v1/medicals/{medicalType}")
public class MedicalController {

	@GetMapping(value = "/facilities")
	public Flux<FacilityResponse> getFacilities(@Valid Location location) {
		return Flux.just(FacilityResponse.of("testType", "testName"),
			FacilityResponse.of("testType2", "testName2"));
	}
}
