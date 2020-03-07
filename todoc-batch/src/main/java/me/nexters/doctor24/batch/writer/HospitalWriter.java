package me.nexters.doctor24.batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.domain.hospital.Hospital;
import me.nexters.domain.hospital.HospitalRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalWriter implements ItemWriter<List<Hospital>> {
	private final HospitalRepository hospitalRepository;

	@Override
	public void write(List<? extends List<Hospital>> items) {
		Long size = items.stream()
			.mapToLong(Collection::size).sum();
		log.info("hospital 배치 Write 시작 {} 개", size);
		items.forEach(
			hospitals -> hospitals
				.forEach(hospital -> {
					if (Objects.nonNull(hospital)) {
						hospitalRepository.save(hospital).block();
					}
				})
		);
	}
}
