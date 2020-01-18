package me.nexters.doctor24.batch.writer;

import java.util.List;
import java.util.Objects;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalWriter implements ItemWriter<List<Hospital>> {
	private final HospitalRepository hospitalRepository;

	@Override
	public void write(List<? extends List<Hospital>> items) {
		items.forEach(
			hospitals -> hospitals
				.forEach(item -> {
					if (Objects.nonNull(item)) {
						hospitalRepository.save(item).block();
					}
				})
		);
	}
}
