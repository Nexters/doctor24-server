package me.nexters.doctor24.batch.reader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.external.publicdata.invoker.PublicDataInvoker;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;

@StepScope
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HospitalReader implements ItemReader<List<HospitalRaw>> {
	private final PublicDataInvoker invoker;

	@Override
	public List<HospitalRaw> read() {
		PageResponse<HospitalRaw> hospitalPage =
			invoker.getHospitalPage(PageRequest.of(1, 250));
		List<HospitalRaw> hospitals = new ArrayList<>(hospitalPage.getContents());
		while (hospitalPage.hasNext()) {
			hospitalPage =
				invoker.getHospitalPage(PageRequest.of(hospitalPage.getNextPage(), 250));
			hospitals.addAll(hospitalPage.getContents());
		}
		return hospitals;
	}
}
