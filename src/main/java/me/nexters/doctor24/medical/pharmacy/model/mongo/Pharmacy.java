package me.nexters.doctor24.medical.pharmacy.model.mongo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.filter.DayFilterTemplate;

@Getter
@ToString
@Builder
@Document(collection = "pharmacy")
public class Pharmacy implements DayFilterTemplate {
	@MongoId
	private String id;
	private String name;
	private List<Day> days;
	private GeoJsonPoint location;
	private String address;
	private String phone;
	private LocalDateTime rowWriteDate;
}
