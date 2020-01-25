package me.nexters.doctor24.batch.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.external.publicdata.invoker.PublicDataInvoker;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;

@StepScope
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalReader implements ItemReader<List<HospitalBasicRaw>> {
	private static final int CALL_ONCE = 1;

	private final PublicDataInvoker invoker;
	private AtomicInteger counter = new AtomicInteger();

	@Override
	public List<HospitalBasicRaw> read() {
		if (isAlreadyInvoked()) {
			return null;
		}

		PageResponse<HospitalBasicRaw> hospitalPage = invoker.getHospitalPage(PageRequest.of(1, 100));
		List<HospitalBasicRaw> hospitalBasicRaws = new ArrayList<>(hospitalPage.getContents());
		while (hospitalPage.hasNext()) {
			hospitalPage =
				invoker.getHospitalPage(PageRequest.of(hospitalPage.getNextPage(), 100));
			hospitalBasicRaws.addAll(hospitalPage.getContents());
		}

		return hospitalBasicRaws;
	}

	private boolean isAlreadyInvoked() {
		int count = counter.getAndIncrement();
		return count > CALL_ONCE;
	}
}
