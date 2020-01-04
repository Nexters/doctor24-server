package me.nexters.doctor24.medical.hospital.model;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalBody {
	private int pageNo;
	private HospitalData items;
}
