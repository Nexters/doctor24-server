package me.nexters.doctor24.batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.domain.pharmacy.Pharmacy;
import me.nexters.domain.pharmacy.PharmacyRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PharmacyCleansingWriter implements ItemWriter<Pharmacy> {
	private final PharmacyRepository pharmacyRepository;

	@Override
	public void write(List<? extends Pharmacy> items) {
		log.info("Pharmacy " + items.size() + "개 삭제");
		pharmacyRepository.deleteAll(items).block();
	}
}
