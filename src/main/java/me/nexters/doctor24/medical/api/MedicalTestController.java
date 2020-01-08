package me.nexters.doctor24.medical.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.api.request.param.Location;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.SwaggerApiTag;
import me.nexters.doctor24.medical.hospital.service.HospitalService;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyRepository;
import reactor.core.publisher.Flux;

// TODO 테스트 용 나중에 지울 것임
@Tag(name = SwaggerApiTag.MEDICAL, description = "[테스트] 의료 서비스 정보 API")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/medicals")
public class MedicalTestController {
	private final HospitalService hospitalService;
	private final PharmacyRepository pharmacyRepository;

	@GetMapping("/pharmacy")
	public ResponseEntity<List<Pharmacy>> findAllPharmacy() {
		return ResponseEntity.ok(pharmacyRepository.findAll());
	}

	@Operation(summary = "[테스트] 특정 위치에 병원 의료 서비스 목륵을 제공한다",
		description = "[테스트] search hospital service by interfaceId",
		tags = {SwaggerApiTag.MEDICAL_TEST})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "NOT FOUND"),
		@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema =
		@Schema(implementation = FacilityResponse.class)))})
	@GetMapping("/hospital")
	public Flux<List<FacilityResponse>> getHospitalsWithin(@RequestBody @Valid Location location) {
		return Flux.just(hospitalService.getHospitalsWithin(location));
	}
}
