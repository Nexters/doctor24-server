package me.nexters.doctor24.batch.processor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.processor.util.HospitalParser;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalDetailFailureProcessor implements ItemProcessor<List<HospitalDetailRaw>, List<Hospital>> {

	@Override
	public List<Hospital> process(List<HospitalDetailRaw> items) {
		log.info("누락건 재배치 process 시작, 총 {} 개", items.size());
		return items.stream().map(HospitalParser::parse).collect(Collectors.toList());
	}
}
