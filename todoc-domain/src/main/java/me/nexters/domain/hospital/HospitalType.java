package me.nexters.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum HospitalType {
	GENERAL("종합병원"),
	NORMAL("병원"),
	CLINIC("의원"),
	NURSING("요양병원"),
	CHINESE("한방병원"),
	UNKNOWN("알수없음");

	private final String type;

	private static final Map<String, HospitalType> holder =
		Collections.unmodifiableMap(Stream.of(HospitalType.values())
			.collect(Collectors.toMap(HospitalType::getType, Function.identity())));

	public static HospitalType find(String type) {
		return Optional.ofNullable(holder.get(type)).orElse(UNKNOWN);
	}
}
