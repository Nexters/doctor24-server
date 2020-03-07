package me.nexters.doctor24.medical.aggregator;

import me.nexters.doctor24.medical.api.request.param.RadiusLevel;
import me.nexters.doctor24.medical.api.response.FacilitiesResponse;
import me.nexters.doctor24.medical.api.response.FacilityIndexResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.domain.common.Day;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author manki.kim
 */
@Service
public class MedicalAggregatorProxy {
	private final Map<MedicalType, MedicalAggregator> aggregatorMap;
	private static final int LIMIT_REQUEST = 20;

	public MedicalAggregatorProxy(List<MedicalAggregator> medicalAggregators) {
		aggregatorMap = medicalAggregators.stream()
			.collect(Collectors.toMap(MedicalAggregator::type, Function.identity()));
	}

	public Flux<FacilitiesResponse> getFacilitiesBy(MedicalType type, double latitude, double longitude,
													String category, Day requestDay, RadiusLevel radiusLevel) {
		return convertFrom(Optional.ofNullable(aggregatorMap.get(type))
			.map(aggregator -> getFacilitiesByConditions(aggregator, latitude, longitude, radiusLevel, category,
				requestDay))
			.map(facilityResponseFlux -> facilityResponseFlux
				.map(FacilityIndexResponse::markNightTimeServe))
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원 하지 않는 medical type 입니다 " + type))
			.limitRequest(LIMIT_REQUEST));
	}

	public Mono<FacilityResponse> getFacilityBy(MedicalType type, String facilityId,
		Day requestDay) {
		return Optional.ofNullable(aggregatorMap.get(type))
			.map(aggregator -> aggregator.getFacilityBy(facilityId))
			.map(facilityResponseMono -> facilityResponseMono
				.map(facilityResponse -> facilityResponse.markToday(requestDay))
				.map(facilityResponse -> facilityResponse.markNightTimeServe(requestDay)))
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원 하지 않는 medical type 입니다 " + type));
	}

	private Flux<FacilitiesResponse> convertFrom(Flux<FacilityIndexResponse> facilityResponseFlux) {
		return facilityResponseFlux.collectList()
			.map(facilityResponses -> facilityResponses.stream()
				.collect(groupingBy(this::generateKey)))
			.map(Map::values)
			.flatMapMany(Flux::fromIterable)
			.map(FacilitiesResponse::of);
	}

	private Flux<FacilityIndexResponse> getFacilitiesByConditions(MedicalAggregator aggregator, double latitude,
		double longitude, RadiusLevel radiusLevel, String category, Day requestDay) {
		return !StringUtils.isEmpty(category) ?
			aggregator.getFacilitiesFilteringByCategoryAndDay(latitude, longitude, radiusLevel.getRangeKM(),
				radiusLevel.getInquiryCount(), category, requestDay)
			: aggregator.getFacilitiesFilteringByDay(latitude, longitude,
			radiusLevel.getRangeKM(), radiusLevel.getInquiryCount(), requestDay);
	}

	private String generateKey(FacilityIndexResponse facilityResponse) {
		return String.join("-",
			String.valueOf(facilityResponse.getLatitude()), String.valueOf(facilityResponse.getLongitude()));
	}
}
