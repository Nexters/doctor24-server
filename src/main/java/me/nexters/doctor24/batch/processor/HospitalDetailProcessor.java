package me.nexters.doctor24.batch.processor;

import java.util.Collections;
import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.processor.util.CategoryParser;
import me.nexters.doctor24.external.publicdata.invoker.PublicDataInvoker;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalDetailProcessor implements ItemProcessor<Hospital, Hospital> {
	private final PublicDataInvoker invoker;

	@Override
	public Hospital process(Hospital hospital) {
		Optional<HospitalDetailRaw> hospitalDetailRaw = invoker.getHospitalDetailPage(hospital.getId());
		if (hospitalDetailRaw.isEmpty()) {
			return hospital;
		}
		if (hospitalDetailRaw.get().getCategories() == null) {
			log.info("hospital category 없음 {}", hospital.getId());
			hospital.updateCategories(Collections.emptyList());
			return hospital;
		}
		hospital.updateCategories(CategoryParser.parse(hospitalDetailRaw.get().getCategories()));
		return hospital;
	}
}
