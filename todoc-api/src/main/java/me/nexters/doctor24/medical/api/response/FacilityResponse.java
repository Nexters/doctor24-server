package me.nexters.doctor24.medical.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import me.nexters.doctor24.medical.api.response.util.ResponseUtil;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.domain.common.Day;
import me.nexters.domain.corona.CoronaHospital;
import me.nexters.domain.hospital.Hospital;
import me.nexters.domain.pharmacy.Pharmacy;
import me.nexters.domain.secure.SecureHospital;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

	@With
	@Schema(description = "오늘 날짜")
	private Day today;

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
			.address(ResponseUtil.filterAddress(hospital.getAddress()))
			.days(hospital.getDays())
			.longitude(hospital.getLocation().getX())
			.latitude(hospital.getLocation().getY())
			.medicalType(MedicalType.hospital)
			.phone(hospital.getPhone())
			.categories(ResponseUtil.filterCategories(hospital))
			.isEmergency(hospital.isEmergency())
			.build();
	}

	public static FacilityResponse fromCoronaHospital(CoronaHospital hospital) {
		return FacilityResponse.builder()
			.id(hospital.getId())
			.name(hospital.getName())
			.address(ResponseUtil.filterAddress(hospital.getAddress()))
			.days(hospital.getDays())
			.longitude(hospital.getLocation().getX())
			.latitude(hospital.getLocation().getY())
			.medicalType(MedicalType.corona)
			.phone(hospital.getPhone())
			.isEmergency(hospital.isEmergency())
			.build();
	}

	public static FacilityResponse fromSecureHospital(SecureHospital secureHospital) {
		return FacilityResponse.builder()
				.id(secureHospital.getId())
				.name(secureHospital.getName())
				.address(ResponseUtil.filterAddress(secureHospital.getAddress()))
				.days(secureHospital.getDays())
				.longitude(secureHospital.getLocation().getX())
				.latitude(secureHospital.getLocation().getY())
				.medicalType(MedicalType.secure)
				.phone(secureHospital.getPhone())
				.isEmergency(secureHospital.isEmergency())
				.build();
	}

	public static FacilityResponse fromPharmacy(Pharmacy pharmacy) {
		return FacilityResponse.builder()
			.id(pharmacy.getId())
			.name(pharmacy.getName())
			.address(ResponseUtil.filterAddress(pharmacy.getAddress()))
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

	public FacilityResponse markToday(Day requestDay) {
		return this.withToday(getTargetDay(requestDay));
	}

	private Day getTargetDay(Day day) {
		// [예외처리 안함] 필터링에 걸려서 어차피 그날 하지 않는 병원은 걸러진다. matching 이 안될 일이 없음.
		return days.stream().filter(target -> target.getDayType() == day.getDayType())
			.findFirst().orElseThrow(RuntimeException::new);
	}
}
