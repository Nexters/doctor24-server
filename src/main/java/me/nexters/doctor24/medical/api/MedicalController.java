package me.nexters.doctor24.medical.api;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.nexters.doctor24.medical.api.request.param.Location;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.SwaggerApiTag;
import reactor.core.publisher.Flux;

/**
 * @author manki.kim
 */
@Tag(name = SwaggerApiTag.MEDICAL, description = "의료 서비스 정보 API")
@RestController
@RequestMapping("/api/v1/medicals/{medicalType}")
public class MedicalController {

	@Operation(summary = "특정 위치에 특정 카테고리(병원, 약국, 동물병원) 의료 서비스 목륵을 제공한다",
		description = "search medical service by interfaceId",
		tags = {SwaggerApiTag.MEDICAL})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "NOT FOUND"),
		@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema =
		@Schema(implementation = FacilityResponse.class)))})
	@GetMapping(value = "/facilities")
	public Flux<FacilityResponse> getFacilities(@Valid Location location) {
		return Flux.just(FacilityResponse.of("testType", "testName"),
			FacilityResponse.of("testType2", "testName2"));
	}
}
