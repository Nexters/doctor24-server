package me.nexters.doctor24.batch.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.dto.HospitalRaw;
import me.nexters.doctor24.batch.dto.hospital.basic.HospitalBasicRaw;
import me.nexters.doctor24.batch.dto.hospital.detail.HospitalDetailRaw;
import me.nexters.doctor24.batch.processor.util.HospitalParser;
import me.nexters.domain.hospital.Hospital;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalProcessor implements ItemProcessor<HospitalRaw, List<Hospital>> {

	@Override
	public List<Hospital> process(HospitalRaw hospitalRaw) {
		List<HospitalBasicRaw> hospitalBasicRaws = hospitalRaw.getHospitalBasicRaws();
		List<HospitalDetailRaw> hospitalDetailRaws = hospitalRaw.getHospitalDetailRaws();

		log.info("Hospital Processor 시작");
		return hospitalBasicRaws.stream()
			.flatMap(hospitalBasicRaw -> hospitalDetailRaws.stream()
				.filter(hospitalDetailRaw -> hospitalDetailRaw.getId().equals(hospitalBasicRaw.getId()))
				.map(hospitalDetailRaw -> HospitalParser.parse(hospitalBasicRaw, hospitalDetailRaw)))
			.collect(Collectors.toList());
	}
}
