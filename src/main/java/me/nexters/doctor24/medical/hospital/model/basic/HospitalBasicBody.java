package me.nexters.doctor24.medical.hospital.model.basic;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalBasicBody {
	private int pageNo;
	private HospitalBasicData items;
}
