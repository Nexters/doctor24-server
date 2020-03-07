package me.nexters.doctor24.batch.dto.hospital.basic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class HospitalResponse {
	private HospitalBasicBody body;

	public List<HospitalBasicRaw> getHospitals() {
		return body.getItems().getHospitalBasicRaws();
	}
}
