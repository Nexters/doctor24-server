package me.nexters.doctor24.batch.writer;

import java.util.List;

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
public class InvalidHospitalWriter implements ItemWriter<Hospital> {
	private final HospitalRepository hospitalRepository;

	@Override
	public void write(List<? extends Hospital> items) {
		log.info("Hospital " + items.size() + "개 삭제");
		hospitalRepository.deleteAll(items).block();
	}
}
