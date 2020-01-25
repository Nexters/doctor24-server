package me.nexters.doctor24.medical.hospital.model;

import java.util.List;

import lombok.Getter;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;

@Getter
public class HospitalRaw {
	private List<HospitalBasicRaw> hospitalBasicRaws;
	private List<HospitalDetailRaw> hospitalDetailRaws;

	public HospitalRaw(List<HospitalBasicRaw> hospitalBasicRaws,
		List<HospitalDetailRaw> hospitalDetailRaws) {
		this.hospitalBasicRaws = hospitalBasicRaws;
		this.hospitalDetailRaws = hospitalDetailRaws;
	}
}
