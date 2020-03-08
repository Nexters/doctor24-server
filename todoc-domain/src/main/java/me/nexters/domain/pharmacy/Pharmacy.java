package me.nexters.domain.pharmacy;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.nexters.domain.common.Day;
import me.nexters.domain.common.DayFilterTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

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
	private boolean isManaged;
}
