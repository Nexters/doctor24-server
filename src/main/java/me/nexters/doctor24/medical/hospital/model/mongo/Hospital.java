package me.nexters.doctor24.medical.hospital.model.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalType;

@Getter
@ToString
@Builder
@Document(collection = "hospital")
public class Hospital {
	@MongoId
	private String id;
	private String name;
	private List<Day> days;
	//!!주의 GeoJson 객체 타입에서는 경도, 위도 순서대로 좌표를 갖는다 (데이터를 넣을 때 해당 순서대로 넣도록!)
	private GeoJsonPoint location;
	private String address;
	private HospitalType hospitalType;
	private String phone;
	private List<String> categories;

	// TODO 필요한 정보 더 로드할 예정
}