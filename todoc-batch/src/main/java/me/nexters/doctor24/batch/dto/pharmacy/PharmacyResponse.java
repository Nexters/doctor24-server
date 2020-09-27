package me.nexters.doctor24.batch.dto.pharmacy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PharmacyResponse {
	private PharmacyBody body;

	public List<PharmacyRaw> getPharmacies() {
		return Optional.ofNullable(body.getItems())
			.map(PharmacyData::getPharmacyRaws)
			.orElse(Collections.emptyList());
	}
}
