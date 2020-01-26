package me.nexters.doctor24.invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.nexters.doctor24.batch.processor.util.PharmacyParser;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyInquires;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyRepository;

@SpringBootTest
public class PharmacyRawInqueriesTest {
	@Autowired
	private PharmacyRepository pharmacyRepository;

	@Autowired
	private PharmacyInquires pharmacyInquires;

	@Ignore
	void 약국_전체_목록_마이그레이션() {
		PageResponse<PharmacyRaw> pharmacyPage = pharmacyInquires.getPharmacyPage(PageRequest.of(1, 250));

		List<PharmacyRaw> pharmacyRaws = new ArrayList<>(pharmacyPage.getContents());
		while (pharmacyPage.hasNext()) {
			pharmacyPage =
				pharmacyInquires.getPharmacyPage(PageRequest.of(pharmacyPage.getNextPage(), 250));
			pharmacyRaws.addAll(pharmacyPage.getContents());
		}

		pharmacyRaws.parallelStream()
			.map(PharmacyParser::parse)
			.forEach(item -> {
				if (Objects.nonNull(item)) {
					pharmacyRepository.save(item).block();
				}
			});
	}
}
