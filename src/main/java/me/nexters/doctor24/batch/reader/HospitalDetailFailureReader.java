package me.nexters.doctor24.batch.reader;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.InvokeFailureContext;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

@Slf4j
@StepScope
@Component
public class HospitalDetailFailureReader implements ItemReader<Map<String, Hospital>> {
	private static final int CALL_ONCE = 1;

	private AtomicInteger counter = new AtomicInteger();

	@Override
	public Map<String, Hospital> read() {
		if (isAlreadyInvoked()) {
			return null;
		}
		return InvokeFailureContext.getFailedHospitals();
	}

	private boolean isAlreadyInvoked() {
		int count = counter.getAndIncrement();
		return count > CALL_ONCE;
	}
}
