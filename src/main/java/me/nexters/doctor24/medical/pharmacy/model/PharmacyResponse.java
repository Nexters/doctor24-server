package me.nexters.doctor24.medical.pharmacy.model;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PharmacyResponse {
	private PharmacyBody body;

	public List<PharmacyRaw> getPharmacies() {
		return body.getItems().getPharmacyRaws();
	}
}
