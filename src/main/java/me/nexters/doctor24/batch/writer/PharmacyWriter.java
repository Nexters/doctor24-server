package me.nexters.doctor24.batch.writer;

import java.util.List;
import java.util.Objects;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyRepository;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PharmacyWriter implements ItemWriter<List<Pharmacy>> {
	private final PharmacyRepository pharmacyRepository;

	@Override
	public void write(List<? extends List<Pharmacy>> items) {
		System.out.println("write start !! size : " + items.size());
		items.forEach(
			pharmacies -> pharmacies.forEach(pharmacy -> {
				if (Objects.nonNull(pharmacy)) {
					pharmacyRepository.save(pharmacy).block();
				}
			})
		);
		System.out.println("write 끗");
	}
}
