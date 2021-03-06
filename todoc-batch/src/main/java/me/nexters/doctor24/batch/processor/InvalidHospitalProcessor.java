package me.nexters.doctor24.batch.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.domain.hospital.Hospital;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvalidHospitalProcessor implements ItemProcessor<Hospital, Hospital> {
	@Override
	public Hospital process(Hospital item) {
		return item;
	}
}
