package me.nexters.doctor24.batch.writer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
public class HospitalDetailWriter implements ItemWriter<Hospital> {
	private final HospitalRepository hospitalRepository;
	private AtomicInteger counter = new AtomicInteger();

	@Override
	public void write(List<? extends Hospital> items) {
		log.info("디비 write 수행 횟수 : " + counter.getAndIncrement());
		items.forEach(hospital -> hospitalRepository.save(hospital).block());
	}
}
