package me.nexters.doctor24.medical.secure.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.filter.DayFilterTemplate;
import me.nexters.doctor24.medical.hospital.model.HospitalType;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@Builder(toBuilder = true)
@Document(collection = "secure")
public class SecureHospital implements DayFilterTemplate {
    @MongoId
    private String id;
    private String name;
    private List<Day> days;
    private GeoJsonPoint location;
    private String address;
    private HospitalType hospitalType;
    private String phone;
    private boolean isEmergency;
    private LocalDateTime rowWriteDate;
    private boolean isManaged;
}
