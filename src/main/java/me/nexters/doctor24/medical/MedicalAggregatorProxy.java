package me.nexters.doctor24.medical;

import java.time.LocalTime;
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
	private static final LocalTime NIGHT_TIME_SERVE = LocalTime.of(20, 0);

	private final Map<MedicalType, MedicalAggregator> aggregatorMap;

	public MedicalAggregatorProxy(List<MedicalAggregator> medicalAggregators) {
		aggregatorMap = medicalAggregators.stream()
			.collect(Collectors.toMap(MedicalAggregator::type, Function.identity()));
	}

	public Flux<FacilityResponse> getFacilitiesWithIn(MedicalType type, double xlatitude, double xlongitude,
		double zlatitude, double zlongitude, String category, Day requestDay) {
		return Optional.ofNullable(aggregatorMap.get(type))
			.map(aggregator -> aggregator.getFacilitiesWithIn(
				PolygonFactory.getByXZPoints(new Point(xlongitude, xlatitude), new Point(zlongitude, zlatitude)),
				requestDay))
			.map(facilityResponseFlux -> facilityResponseFlux
				.map(facilityResponse -> setNightTimeServe(facilityResponse, requestDay)))
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원 하지 않는 medical type 입니다 " + type));
	}

	public Flux<FacilityResponse> getFacilitiesBy(MedicalType type, double latitude, double longitude,
		String category, Day requestDay) {
		return Optional.ofNullable(aggregatorMap.get(type))
			.map(aggregator -> getFacilitiesByConditions(aggregator, latitude, longitude, category, requestDay))
			.map(facilityResponseFlux -> facilityResponseFlux
				.map(facilityResponse -> setNightTimeServe(facilityResponse, requestDay)))
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "지원 하지 않는 medical type 입니다 " + type));
	}

	private FacilityResponse setNightTimeServe(FacilityResponse facilityResponse, Day requestDay) {
		Optional<Day> targetDay = facilityResponse.getDays().stream()
			.filter(day -> day.isEqual(requestDay)).findFirst();
		if (targetDay.isEmpty()) {
			// target date 이 없을 경우 야간 진료는 없는 것으로 한다.
			facilityResponse.setNightTimeServe(false);
			return facilityResponse;
		}
		if (targetDay.get().getEndTime().isBefore(NIGHT_TIME_SERVE)) {
			facilityResponse.setNightTimeServe(false);
			return facilityResponse;
		}
		facilityResponse.setNightTimeServe(true);
		return facilityResponse;
	}

	private Flux<FacilityResponse> getFacilitiesByConditions(MedicalAggregator aggregator, double latitude,
		double longitude, String category, Day requestDay) {
		return !StringUtils.isEmpty(category) ?
			aggregator.getFacilitiesFilteringByCategoryAndDay(latitude, longitude, category, requestDay)
			: aggregator.getFacilitiesFilteringByDay(latitude, longitude, requestDay);
	}
}
