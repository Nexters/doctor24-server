package me.nexters.doctor24.medical;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import me.nexters.doctor24.medical.api.response.FacilitiesResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.support.PolygonFactory;
import reactor.core.publisher.Flux;

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

	public Flux<FacilitiesResponse> getFacilitiesWithIn(MedicalType type, double xlatitude, double xlongitude,
		double zlatitude, double zlongitude, String category, Day requestDay) {
		return convertFrom(Optional.ofNullable(aggregatorMap.get(type))
			.map(aggregator -> aggregator.getFacilitiesWithIn(
				PolygonFactory.getByXZPoints(new Point(xlongitude, xlatitude), new Point(zlongitude, zlatitude)),
				requestDay))
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원 하지 않는 medical type 입니다 " + type))
			.limitRequest(LIMIT_REQUEST));
	}

	public Flux<FacilitiesResponse> getFacilitiesBy(MedicalType type, double latitude, double longitude,
		String category, Day requestDay) {
		return convertFrom(Optional.ofNullable(aggregatorMap.get(type))
			.map(aggregator -> getFacilitiesByConditions(aggregator, latitude, longitude, category, requestDay))
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원 하지 않는 medical type 입니다 " + type))
			.limitRequest(LIMIT_REQUEST));
	}

	private Flux<FacilitiesResponse> convertFrom(Flux<FacilityResponse> facilityResponseFlux) {
		return facilityResponseFlux
			.collectMap(this::generateKey, Arrays::asList)
			.map(Map::values)
			.flatMapMany(Flux::fromIterable)
			.map(FacilitiesResponse::of);
	}

	private Flux<FacilityResponse> getFacilitiesByConditions(MedicalAggregator aggregator, double latitude,
		double longitude, String category, Day requestDay) {
		return !StringUtils.isEmpty(category) ?
			aggregator.getFacilitiesFilteringByCategoryAndDay(latitude, longitude, category, requestDay)
			: aggregator.getFacilitiesFilteringByDay(latitude, longitude, requestDay);
	}

	private String generateKey(FacilityResponse facilityResponse) {
		return String.join("-",
			String.valueOf(facilityResponse.getLatitude()), String.valueOf(facilityResponse.getLongitude()));
	}
}
