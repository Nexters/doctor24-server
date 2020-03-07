package me.nexters.doctor24.batch.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.InvokeFailureContext;
import me.nexters.doctor24.batch.dto.hospital.detail.HospitalDetailRaw;
import me.nexters.doctor24.publicdata.invoker.PublicDataInvoker;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalDetailFailureReader implements ItemReader<List<HospitalDetailRaw>> {
	private static final int CALL_ONCE = 1;

	private final PublicDataInvoker invoker;
	private AtomicInteger counter = new AtomicInteger();

	@Override
	public List<HospitalDetailRaw> read() {
		if (isAlreadyInvoked()) {
			return null;
		}
		List<String> failedHospitalIds = InvokeFailureContext.getFailedHospitalIds();
		return failedHospitalIds.stream()
			.map(invoker::getHospitalDetailPage)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.collect(Collectors.toList());
	}

	private boolean isAlreadyInvoked() {
		int count = counter.getAndIncrement();
		return count > CALL_ONCE;
	}
}
