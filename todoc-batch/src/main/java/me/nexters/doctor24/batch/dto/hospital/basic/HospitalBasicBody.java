package me.nexters.doctor24.batch.dto.hospital.basic;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalBasicBody {
	private int pageNo;
	private HospitalBasicData items;
}
