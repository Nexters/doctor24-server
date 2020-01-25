package me.nexters.doctor24.batch.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.batch.processor.util.HospitalParser;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalProcessor implements ItemProcessor<List<HospitalBasicRaw>, List<Hospital>> {

	@Override
	public List<Hospital> process(List<HospitalBasicRaw> hospitalBasicRaws) {
		return hospitalBasicRaws.stream()
			.map(HospitalParser::parse)
			.collect(Collectors.toList());
	}
}
