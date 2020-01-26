package me.nexters.doctor24.batch.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class HospitalDetailFailureProcessor implements ItemProcessor<Map<String, Hospital>, List<Hospital>> {
	private final PublicDataInvoker invoker;

	@Override
	public List<Hospital> process(Map<String, Hospital> item) {
		Map<String, Hospital> hospitals = item.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getKey, entry -> {
				Hospital hospital = entry.getValue();
				Optional<HospitalDetailRaw> hospitalDetailRaw = invoker.getHospitalDetailPage(entry.getKey());
				if (hospitalDetailRaw.isEmpty()) {
					log.info("[재배치 실패] hospital id {}", hospital.getId());
					return hospital;
				}
				if (hospitalDetailRaw.get().getCategories() == null) {
					log.info("[재배치] hospital category 없음 {}", hospital.getId());
					hospital.updateCategories(Collections.emptyList());
					return hospital;
				}
				hospital.updateCategories(CategoryParser.parse(hospitalDetailRaw.get().getCategories()));
				return hospital;
			}));

		return new ArrayList<>(hospitals.values());
	}
}
