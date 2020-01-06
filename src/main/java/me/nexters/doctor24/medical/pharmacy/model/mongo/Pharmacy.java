package me.nexters.doctor24.medical.pharmacy.model.mongo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import me.nexters.doctor24.medical.common.Day;

@Getter
@ToString
@Builder
@EqualsAndHashCode(of = "id")
@Document(collection = "pharmacy")
public class Pharmacy {

	@MongoId
	private String id;
	private String name;
	private List<Day> days;
	private double latitude;
	private double longitude;
	private String address;
	private String phone;

	// TODO 필요한 정보 더 로드할 예정
}
