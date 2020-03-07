package me.nexters.doctor24.batch.dto.pharmacy;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PharmacyResponse {
	private PharmacyBody body;

	public List<PharmacyRaw> getPharmacies() {
		return body.getItems().getPharmacyRaws();
	}
}
