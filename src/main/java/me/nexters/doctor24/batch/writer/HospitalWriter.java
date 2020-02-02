package me.nexters.doctor24.batch.writer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalWriter implements ItemWriter<List<Hospital>> {
	private final HospitalRepository hospitalRepository;

	@Override
	public void write(List<? extends List<Hospital>> items) {
		Long size = items.stream()
			.mapToLong(Collection::size).sum();
		log.info("hospital 배치 Write 시작 {} 개", size);		items.forEach(
			hospitals -> hospitals
				.forEach(hospital -> {
					if (Objects.nonNull(hospital)) {
						hospitalRepository.save(hospital).block();
					}
				})
		);
	}
}
