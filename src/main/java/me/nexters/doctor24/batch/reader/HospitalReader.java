package me.nexters.doctor24.batch.reader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.InvokeFailureContext;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.external.publicdata.invoker.PublicDataInvoker;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalReader implements ItemReader<HospitalRaw> {
	private static final int CALL_ONCE = 1;

	private final PublicDataInvoker invoker;
	private AtomicInteger counter = new AtomicInteger();

	@Override
	public HospitalRaw read() {
		if (isAlreadyInvoked()) {
			return null;
		}

		PageResponse<HospitalBasicRaw> hospitalBasicPage = invoker.getHospitalPage(PageRequest.of(1, 150));
		List<HospitalBasicRaw> hospitalBasicRaws = new ArrayList<>(hospitalBasicPage.getContents());
		while (hospitalBasicPage.hasNext()) {
			hospitalBasicPage =
				invoker.getHospitalPage(PageRequest.of(hospitalBasicPage.getNextPage(), 150));
			hospitalBasicRaws.addAll(hospitalBasicPage.getContents());
		}

		log.info("Basic Read Finished");
		List<HospitalDetailRaw> hospitalDetailRaws = new ArrayList<>();
		hospitalBasicRaws.forEach(basicRaw -> {
			HospitalDetailRaw hospitalDetailRaw = getHospitalDetailRaw(basicRaw);
			hospitalDetailRaws.add(hospitalDetailRaw);
		});

		log.info("Detail Read Finished");
		return new HospitalRaw(hospitalBasicRaws, hospitalDetailRaws);
	}

	private HospitalDetailRaw getHospitalDetailRaw(HospitalBasicRaw basicRaw) {
		HospitalDetailRaw hospitalDetailRaw = invoker.getHospitalDetailPage(basicRaw.getId())
			.orElseGet(() -> checkOmission(basicRaw));
		// 어차피 없는 데이터인데 기록이 필요할까
		//checkCategories(hospitalDetailRaw);
		return hospitalDetailRaw;
	}

	private HospitalDetailRaw checkOmission(HospitalBasicRaw basicRaw) {
		log.info("hospital 누락 {}", basicRaw.getId());
		InvokeFailureContext.add(basicRaw.getId());
		HospitalDetailRaw raw = new HospitalDetailRaw();
		raw.setId(basicRaw.getId());
		raw.setCategories("");
		return raw;
	}

	private void checkCategories(HospitalDetailRaw hospitalDetailRaw) {
		if (hospitalDetailRaw.getCategories() == null) {
			log.info("hospital category 없음 {}", hospitalDetailRaw.getId());
		}
	}

	private boolean isAlreadyInvoked() {
		int count = counter.getAndIncrement();
		return count > CALL_ONCE;
	}
}
