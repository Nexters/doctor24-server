package me.nexters.domain.hospital;

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
@Document(collection = "hospital")
public class Hospital implements DayFilterTemplate {
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
    private boolean isEmergency;
    private LocalDateTime rowWriteDate;
    private boolean isManaged;

    public void updateCategories(List<String> categories) {
        this.categories = categories;
        this.rowWriteDate = LocalDateTime.now();
    }
}