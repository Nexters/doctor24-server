package me.nexters.doctor24.medical.hospital.model.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import me.nexters.doctor24.medical.hospital.model.HospitalType;

@Getter
@ToString
@Builder
@EqualsAndHashCode(of = "id")
@Document(collection = "hospital")
public class Hospital {

	@MongoId
	private String id;
	private String name;
	private List<Day> days;
	private double latitude;
	private double longitude;
	private String address;
	private HospitalType hospitalType;
	private String phone;

	// TODO 필요한 정보 더 로드할 예정
}