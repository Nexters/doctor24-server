package me.nexters.doctor24.medical.pharmacy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PharmacyData {
	@JsonProperty("item")
	private List<PharmacyRaw> pharmacyRaws;
}
