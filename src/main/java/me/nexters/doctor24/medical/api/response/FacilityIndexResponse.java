package me.nexters.doctor24.medical.api.response;

import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.With;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

/**
 * @author manki.kim
 */
@Getter
@ToString
@Builder
public class FacilityIndexResponse {
	private static final LocalTime NIGHT_TIME_SERVE = LocalTime.of(20, 0);

	@Schema(description = "고유 아이디")
	private String id;

	@Schema(description = "지점 이름")
	private String name;

	@Schema(description = "위도")
	private double latitude;

	@Schema(description = "경도")
	private double longitude;

	@Schema(description = "의료 서비스 카테고리")
	private MedicalType medicalType;

	@With
	@Schema(description = "야간 진료 유무")
	private boolean isNightTimeServe;

	@Schema(description = "응급실 유무")
	private boolean isEmergency;

	@Schema(description = "운영시간")
	private Day day;

	@Schema(description = "전화번호")
	private String phone;

	@Schema(description = "주소")
	private String address;

	@Schema(description = "진료과목 (hospital type만 존재)")
	private List<String> categories;

	public static FacilityIndexResponse fromHospital(Hospital hospital, Day requestDay) {
		return FacilityIndexResponse.builder()
			.id(hospital.getId())
			.name(hospital.getName())
			.address(hospital.getAddress())
			.day(getToday(hospital.getDays(), requestDay))
			.longitude(hospital.getLocation().getX())
			.latitude(hospital.getLocation().getY())
			.medicalType(MedicalType.hospital)
			.phone(hospital.getPhone())
			.isEmergency(hospital.isEmergency())
			.categories(hospital.getCategories())
			.build();
	}

	public static FacilityIndexResponse fromPharmacy(Pharmacy pharmacy, Day requestDay) {
		return FacilityIndexResponse.builder()
			.id(pharmacy.getId())
			.name(pharmacy.getName())
			.address(pharmacy.getAddress())
			.day(getToday(pharmacy.getDays(), requestDay))
			.longitude(pharmacy.getLocation().getX())
			.latitude(pharmacy.getLocation().getY())
			.medicalType(MedicalType.pharmacy)
			.phone(pharmacy.getPhone())
			.build();
	}

	public FacilityIndexResponse markNightTimeServe() {
		return this.day.getEndTime().isBefore(NIGHT_TIME_SERVE) ?
			this.withNightTimeServe(false) : this.withNightTimeServe(true);
	}

	private static Day getToday(List<Day> days, Day requestDay) {
		return days.stream()
			.filter(day -> day.isEqual(requestDay)).findAny()
			.orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "해당 날짜의 운영시간 정보가 없습니다 " + requestDay));
	}
}
