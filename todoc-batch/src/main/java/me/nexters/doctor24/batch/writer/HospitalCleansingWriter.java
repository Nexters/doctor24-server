package me.nexters.doctor24.batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.domain.hospital.Hospital;
import me.nexters.domain.hospital.HospitalRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalCleansingWriter implements ItemWriter<Hospital> {
	private final HospitalRepository hospitalRepository;

	@Override
	public void write(List<? extends Hospital> items) {
		log.info("Hospital " + items.size() + "개 삭제");
		hospitalRepository.deleteAll(items).block();
	}
}
