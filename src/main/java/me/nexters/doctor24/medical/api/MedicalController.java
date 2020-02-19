package me.nexters.doctor24.medical.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.MedicalAggregatorProxy;
import me.nexters.doctor24.medical.api.request.filter.OperatingHoursFilterWrapper;
import me.nexters.doctor24.medical.api.request.param.RadiusLevel;
import me.nexters.doctor24.medical.api.response.FacilitiesResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.api.type.SwaggerApiTag;
import me.nexters.doctor24.medical.holiday.HolidayManager;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
@Slf4j
@Tag(name = SwaggerApiTag.MEDICAL, description = "의료 서비스 정보 API")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/medicals/{type}")
public class MedicalController {
	private final MedicalAggregatorProxy aggregatorProxy;
	private final HolidayManager holidayManager;

	@Operation(summary = "특정 위치에 반경 500m 안에 특정 카테고리(병원, 약국, 동물병원) 의료 서비스 목륵을 제공한다",
		description = "search medical service by interfaceId",
		tags = {SwaggerApiTag.MEDICAL})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "NOT FOUND"),
		@ApiResponse(responseCode = "200", description = "successful operation")})
	@GetMapping(value = "/latitudes/{latitude}/longitudes/{longitude}/facilities")
	public Flux<FacilitiesResponse> getFacilities(
		@PathVariable MedicalType type, @PathVariable String latitude,
		@PathVariable String longitude,
		@RequestParam(required = false) String category,
		@Valid @Parameter(style = ParameterStyle.DEEPOBJECT) OperatingHoursFilterWrapper operatingHoursFilterWrapper,
		@Parameter(description = "1(0.5km), 2(1km), 3(1.5km) ,4(2km) 단계의 반경범위")
		@RequestParam(defaultValue = "1") int radiusLevel) {
		log.info("[RADIUS LEVEL] {} {} {}", latitude, longitude, radiusLevel);
		return aggregatorProxy.getFacilitiesBy(type, Double.parseDouble(latitude),
			Double.parseDouble(longitude), category, operatingHoursFilterWrapper.getDay(holidayManager),
			RadiusLevel.getBy(radiusLevel));
	}

	@Operation(summary = "특정 의료 기관 상세보기 API",
		description = "search medical service by facilityId",
		tags = {SwaggerApiTag.MEDICAL})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "404", description = "NOT FOUND"),
		@ApiResponse(responseCode = "200", description = "successful operation")})
	@GetMapping(value = "/facilities/{facilityId}")
	public Mono<FacilityResponse> getFacility(
		@Valid @Parameter(style = ParameterStyle.DEEPOBJECT) OperatingHoursFilterWrapper operatingHoursFilterWrapper,
		@PathVariable MedicalType type, @PathVariable String facilityId) {
		return aggregatorProxy.getFacilityBy(type, facilityId, operatingHoursFilterWrapper.getDay(holidayManager));
	}
}
