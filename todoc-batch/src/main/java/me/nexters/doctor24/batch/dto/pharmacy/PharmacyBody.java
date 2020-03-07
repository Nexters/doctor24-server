package me.nexters.doctor24.batch.dto.pharmacy;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PharmacyBody {
	private int pageNo;
	private PharmacyData items;
}
