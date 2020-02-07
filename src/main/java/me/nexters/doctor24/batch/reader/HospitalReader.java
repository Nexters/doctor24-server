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
import me.nexters.doctor24.batch.processor.util.CategoryParser;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.external.publicdata.invoker.PublicDataInvoker;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalReader implements ItemReader<HospitalRaw> {
	private static final int CALL_ONCE = 1;

	private final PublicDataInvoker invoker;
	private final HospitalRepository hospitalRepository;
	private AtomicInteger counter = new AtomicInteger();

	@Override
	public HospitalRaw read() {
		if (isAlreadyInvoked()) {
			return null;
		}

		PageResponse<HospitalBasicRaw> hospitalBasicPage = invoker.getHospitalPage(PageRequest.of(1, 150));
		List<HospitalBasicRaw> hospitalBasicRaws = new ArrayList<>(hospitalBasicPage.getContents());
		while (hospitalBasicPage.hasNext()) {
			PageResponse<HospitalBasicRaw> result = invoker.getHospitalPage(
				PageRequest.of(hospitalBasicPage.getNextPage(), 150));
			if (result == null) {
				continue;
			}
			hospitalBasicPage = result;
			hospitalBasicRaws.addAll(hospitalBasicPage.getContents());
		}

		log.info("Basic Read Finished! page number : {}", hospitalBasicPage.getPage());
		List<HospitalDetailRaw> hospitalDetailRaws = new ArrayList<>();
		hospitalBasicRaws.forEach(basicRaw -> parse(hospitalDetailRaws, basicRaw));
		log.info("Detail Read Finished");
		return new HospitalRaw(hospitalBasicRaws, hospitalDetailRaws);
	}

	private void parse(List<HospitalDetailRaw> hospitalDetailRaws, HospitalBasicRaw basicRaw) {
		Hospital hospital = hospitalRepository.findById(basicRaw.getId()).block();
		if (hospital == null) {
			addNewHospital(hospitalDetailRaws, basicRaw);
			return;
		}
		addExistingHospital(hospitalDetailRaws, basicRaw, hospital);
	}

	private void addNewHospital(List<HospitalDetailRaw> hospitalDetailRaws, HospitalBasicRaw basicRaw) {
		log.info("새로운 병원 추가! 아이디 : {}, 이름 : {}", basicRaw.getId(), basicRaw.getName());
		HospitalDetailRaw hospitalDetailRaw = getHospitalDetailRaw(basicRaw.getId());
		hospitalDetailRaws.add(hospitalDetailRaw);
	}

	private void addExistingHospital(List<HospitalDetailRaw> hospitalDetailRaws, HospitalBasicRaw basicRaw,
		Hospital hospital) {
		HospitalDetailRaw hospitalDetailRaw = new HospitalDetailRaw();
		hospitalDetailRaw.setId(basicRaw.getId());
		hospitalDetailRaw.setCategories(CategoryParser.toRaw(hospital.getCategories()));
		checkManaged(hospital, hospitalDetailRaw);
		hospitalDetailRaws.add(hospitalDetailRaw);
	}

	private void checkManaged(Hospital hospital, HospitalDetailRaw hospitalDetailRaw) {
		if (hospital.isManaged()) {
			hospitalDetailRaw.setLongitude(hospital.getLocation().getX());
			hospitalDetailRaw.setLatitude(hospital.getLocation().getY());
			hospitalDetailRaw.setManaged(true);
		}
	}

	private HospitalDetailRaw getHospitalDetailRaw(String id) {
		return invoker.getHospitalDetailPage(id).orElseGet(() -> handleOmission(id));
	}

	private HospitalDetailRaw handleOmission(String id) {
		log.info("hospital 누락 {}", id);
		InvokeFailureContext.add(id);
		HospitalDetailRaw raw = new HospitalDetailRaw();
		raw.setId(id);
		raw.setCategories("");
		return raw;
	}

	private boolean isAlreadyInvoked() {
		int count = counter.getAndIncrement();
		return count >= CALL_ONCE;
	}
}
