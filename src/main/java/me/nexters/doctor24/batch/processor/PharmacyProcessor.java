package me.nexters.doctor24.batch.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.processor.util.PharmacyParser;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;

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
