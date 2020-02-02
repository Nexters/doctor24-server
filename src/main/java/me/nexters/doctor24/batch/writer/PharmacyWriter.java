package me.nexters.doctor24.batch.writer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyRepository;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PharmacyWriter implements ItemWriter<List<Pharmacy>> {
	private final PharmacyRepository pharmacyRepository;

	@Override
	public void write(List<? extends List<Pharmacy>> items) {
		Long size = items.stream()
			.mapToLong(Collection::size).sum();
		log.info("pharmacy 배치 Write 시작 {} 개", size);		items.forEach(
			pharmacies -> pharmacies.forEach(pharmacy -> {
				if (Objects.nonNull(pharmacy)) {
					pharmacyRepository.save(pharmacy).block();
				}
			})
		);
	}
}
