package me.nexters.doctor24.medical.api.response;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

/**
 * @author manki.kim
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacilityResponse {
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
	private List<Day> days;

	@Schema(description = "전화번호")
	private String phone;

	@Schema(description = "주소")
	private String address;

	@Schema(description = "진료과목 (hospital type만 존재)")
	private List<String> categories;

	public static FacilityResponse fromHospital(Hospital hospital) {
		return FacilityResponse.builder()
			.id(hospital.getId())
			.name(hospital.getName())
			.address(hospital.getAddress())
			.days(hospital.getDays())
			.longitude(hospital.getLocation().getX())
			.latitude(hospital.getLocation().getY())
			.medicalType(MedicalType.hospital)
			.phone(hospital.getPhone())
			.categories(hospital.getCategories())
			.isEmergency(hospital.isEmergency())
			.build();
	}

	public static FacilityResponse fromPharmacy(Pharmacy pharmacy) {
		return FacilityResponse.builder()
			.id(pharmacy.getId())
			.name(pharmacy.getName())
			.address(pharmacy.getAddress())
			.days(pharmacy.getDays())
			.longitude(pharmacy.getLocation().getX())
			.latitude(pharmacy.getLocation().getY())
			.medicalType(MedicalType.pharmacy)
			.phone(pharmacy.getPhone())
			.build();
	}

	public FacilityResponse markNightTimeServe(Day requestDay) {
		Optional<Day> targetDay = days.stream()
			.filter(day -> day.isEqual(requestDay)).findFirst();
		if (targetDay.isEmpty()) {
			// target date 이 없을 경우 야간 진료는 없는 것으로 한다.
			return this.withNightTimeServe(false);
		}
		return targetDay.get().getEndTime().isBefore(NIGHT_TIME_SERVE) ?
			this.withNightTimeServe(false) : this.withNightTimeServe(true);
	}
}
