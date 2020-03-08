package me.nexters.doctor24.batch.reader;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.batch.dto.pharmacy.PharmacyRaw;
import me.nexters.doctor24.page.PageRequest;
import me.nexters.doctor24.page.PageResponse;
import me.nexters.doctor24.publicdata.invoker.PublicDataInvoker;
import me.nexters.domain.pharmacy.Pharmacy;
import me.nexters.domain.pharmacy.PharmacyRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@StepScope
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PharmacyReader implements ItemReader<List<PharmacyRaw>> {
	private static final int CALL_ONCE = 1;

	private final PublicDataInvoker invoker;
	private final PharmacyRepository pharmacyRepository;

	private AtomicInteger counter = new AtomicInteger();

	@Override
	public List<PharmacyRaw> read() {
		if (isAlreadyInvoked()) {
			return null;
		}
		PageResponse<PharmacyRaw> pharmacyPage = invoker.getPharmacyPage(PageRequest.of(1, 150));
		List<PharmacyRaw> pharmacyRaws = new ArrayList<>(pharmacyPage.getContents());
		while (pharmacyPage.hasNext()) {
			PageResponse<PharmacyRaw> result = invoker.getPharmacyPage(
				PageRequest.of(pharmacyPage.getNextPage(), 150));
			if (result == null) {
				continue;
			}
			pharmacyPage = result;
			pharmacyRaws.addAll(pharmacyPage.getContents());
		}
		return pharmacyRaws.stream().filter(this::isNotManaged).collect(Collectors.toList());
	}

	private boolean isAlreadyInvoked() {
		int count = counter.getAndIncrement();
		return count >= CALL_ONCE;
	}

	private boolean isNotManaged(PharmacyRaw pharmacyRaw) {
		Pharmacy pharmacy = pharmacyRepository.findById(pharmacyRaw.getId()).block();
		if (pharmacy == null) {
			return true;
		}
		return !pharmacy.isManaged();
	}
}
