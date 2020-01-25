package me.nexters.doctor24.medical;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import reactor.core.publisher.Flux;

/**
 * @author manki.kim
 */
@Service
public class MedicalAggregatorProxy {
	private final Map<MedicalType, MedicalAggregator> aggregatorMap;

	public MedicalAggregatorProxy(List<MedicalAggregator> medicalAggregators) {
		aggregatorMap = medicalAggregators.stream()
			.collect(Collectors.toMap(MedicalAggregator::type, Function.identity()));
	}

	public Flux<FacilityResponse> getFacilitiesBy(MedicalType type, double latitude, double longitude,
		String category, Day requestDay) {
		return Optional.ofNullable(aggregatorMap.get(type))
			.map(aggregator -> getFacilitiesByConditions(aggregator, latitude, longitude, category, requestDay))
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원 하지 않는 medical type 입니다 " + type));
	}

	private Flux<FacilityResponse> getFacilitiesByConditions(MedicalAggregator aggregator, double latitude,
		double longitude, String category, Day requestDay) {
		return !StringUtils.isEmpty(category) ?
			aggregator.getFacilitiesFilteringByCategoryAndDay(latitude, longitude, category, requestDay)
			: aggregator.getFacilitiesFilteringByDay(latitude, longitude, requestDay);
	}
}
