package me.nexters.doctor24.medical.api.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
	@Schema(description = "지점 이름")
	private String name;

	@Schema(description = "위도")
	private double latitude;

	@Schema(description = "경도")
	private double longitude;

	@Schema(description = "의료 서비스 카테고리")
	private MedicalType medicalType;

	@Schema(description = "운영시간")
	private List<Day> days;

	@Schema(description = "전화번호")
	private String phone;

	@Schema(description = "주소")
	private String address;

	public static FacilityResponse fromHospital(Hospital hospital) {
		return FacilityResponse.builder()
			.name(hospital.getName())
			.address(hospital.getAddress())
			.days(hospital.getDays())
			.longitude(hospital.getLocation().getX())
			.latitude(hospital.getLocation().getY())
			.medicalType(MedicalType.hospital)
			.phone(hospital.getPhone())
			.build();
	}

	public static FacilityResponse fromPharmacy(Pharmacy pharmacy) {
		return FacilityResponse.builder()
			.name(pharmacy.getName())
			.address(pharmacy.getAddress())
			.days(pharmacy.getDays())
			.longitude(pharmacy.getLocation().getX())
			.latitude(pharmacy.getLocation().getY())
			.medicalType(MedicalType.pharmacy)
			.phone(pharmacy.getPhone())
			.build();
	}
}
