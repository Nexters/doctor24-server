package me.nexters.doctor24.medical.hospital.model;

import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

	public static HospitalType find(String type) {
		return Stream.of(values())
			.filter(hospitalType -> hospitalType.type.equals(type))
			.findAny()
			.orElse(UNKNOWN);
	}
}
