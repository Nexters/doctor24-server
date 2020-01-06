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
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.repository.HospitalInquires;

@SpringBootTest
class HospitalRawInquiresTest {
	@Autowired
	private HospitalInquires hospitalInquires;

	@Test
	void 전국_병원_인덱스() {
		PageResponse<HospitalRaw> hospitalPage =
			hospitalInquires.getHospitalPage(PageRequest.of(2, 100));
		assertThat(hospitalPage.getContents().size(), is(100));
	}

	@Test
	void 강남구_병원_호출() {
		PageResponse<HospitalRaw> hospitalsByCityAndProvinceOrderBy = hospitalInquires.getHospitalsByCityAndProvinceOrderBy(
			PageRequest.of(1, 100), "서울특별시", "강남구");

		assertThat(hospitalsByCityAndProvinceOrderBy.getContents().size(), is(100));
	}

	@Test
	void 전체_조회_샘플() {
		PageResponse<HospitalRaw> hospitalPage =
			hospitalInquires.getHospitalPage(PageRequest.of(1, 100));
		List<HospitalRaw> hospitals = new ArrayList<>(hospitalPage.getContents());
		while (hospitalPage.hasNext()) {
			hospitalPage =
				hospitalInquires.getHospitalPage(PageRequest.of(hospitalPage.getNextPage(),  100));
			hospitals.addAll(hospitalPage.getContents());
		}

		System.out.println(hospitals.size());
	}
}