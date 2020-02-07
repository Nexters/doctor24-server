package me.nexters.doctor24.batch.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.processor.util.HospitalParser;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

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
