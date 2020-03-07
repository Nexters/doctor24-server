package me.nexters.doctor24.batch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.InvokeFailureContext;
import me.nexters.domain.hospital.Hospital;
import me.nexters.domain.hospital.HospitalRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalDetailFailureWriter implements ItemWriter<List<Hospital>> {
	private final HospitalRepository hospitalRepository;

	@Override
	public void write(List<? extends List<Hospital>> items) {
		if (checkFailureContext(items)) {
			log.info("재배치 실패건 적재시작");
		}
		items.forEach(
			hospitals -> hospitals
				.forEach(item -> {
					if (Objects.nonNull(item)) {
						hospitalRepository.save(item).block();
					}
				})
		);
		clearContext();
	}

	private boolean checkFailureContext(List<? extends List<Hospital>> items) {
		return items.size() != InvokeFailureContext.size();
	}

	private void clearContext() {
		InvokeFailureContext.clear();
	}
}
