package me.nexters.doctor24.batch.dto.pharmacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PharmacyData {
	@JsonProperty("item")
	private List<PharmacyRaw> pharmacyRaws;
}
