package me.nexters.domain.corona;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.nexters.domain.common.Day;
import me.nexters.domain.common.DayFilterTemplate;
import me.nexters.domain.hospital.HospitalType;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author manki.kim
 */
@Getter
@ToString
@Builder(toBuilder = true)
@Document(collection = "corona")
public class CoronaHospital implements DayFilterTemplate {
	@MongoId
	private String id;
	private String name;
	private List<Day> days;
	//!!주의 GeoJson 객체 타입에서는 경도, 위도 순서대로 좌표를 갖는다 (데이터를 넣을 때 해당 순서대로 넣도록!)
	private GeoJsonPoint location;
	private String address;
	private HospitalType hospitalType;
	private String phone;
	private boolean isEmergency;
	private LocalDateTime rowWriteDate;
	private boolean isManaged;
}
