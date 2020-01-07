package me.nexters.doctor24.medical.pharmacy.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PharmacyBody {
	private int pageNo;
	private PharmacyData items;
}
