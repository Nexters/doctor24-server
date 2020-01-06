package me.nexters.doctor24.invoker;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.medical.hospital.model.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

@SpringBootTest
class HospitalRepositoryTest {
	@Autowired
	private HospitalRepository hospitalRepository;

	@Test
	void 전국_병원_인덱스() {
		PageResponse<Hospital> hospitalPage =
			hospitalRepository.getHospitalPage(PageRequest.of(2, 100));
		assertThat(hospitalPage.getContents().size(), is(100));
	}

	@Test
	void 전체_조회_샘플() {
		PageResponse<Hospital> hospitalPage =
			hospitalRepository.getHospitalPage(PageRequest.of(1, 100));
		List<Hospital> hospitals = new ArrayList<>(hospitalPage.getContents());
		while (hospitalPage.hasNext()) {
			hospitalPage =
				hospitalRepository.getHospitalPage(PageRequest.of(hospitalPage.getNextPage(), 2000));
			hospitals.addAll(hospitalPage.getContents());
		}

		System.out.println(hospitals.size());
	}
}