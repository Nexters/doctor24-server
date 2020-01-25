package me.nexters.doctor24.medical.hospital.model.detail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HospitalDetailResponse {
	private HospitalDetailBody body;

	public HospitalDetailRaw getHospitalDetail() {
		return body.getItems().getHospitalDetailRaws();
	}
}
