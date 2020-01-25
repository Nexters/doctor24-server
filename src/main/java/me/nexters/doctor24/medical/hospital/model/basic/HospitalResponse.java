package me.nexters.doctor24.medical.hospital.model.basic;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class HospitalResponse {
	private HospitalBasicBody body;

	public List<HospitalBasicRaw> getHospitals() {
		return body.getItems().getHospitalBasicRaws();
	}
}
