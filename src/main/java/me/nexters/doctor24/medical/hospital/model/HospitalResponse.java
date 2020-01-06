package me.nexters.doctor24.medical.hospital.model;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalResponse {
	private HospitalBody body;

	public List<HospitalRaw> getHospitals() {
		return body.getItems().getHospitalRaws();
	}
}
