package me.nexters.doctor24.batch.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.dto.pharmacy.PharmacyRaw;
import me.nexters.doctor24.batch.processor.util.PharmacyParser;
import me.nexters.domain.pharmacy.Pharmacy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PharmacyProcessor implements ItemProcessor<List<PharmacyRaw>, List<Pharmacy>> {
	@Override
	public List<Pharmacy> process(List<PharmacyRaw> pharmacyRaws) {
		return pharmacyRaws.stream()
			.map(PharmacyParser::parse)
			.collect(Collectors.toList());
	}
}
