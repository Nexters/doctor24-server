package me.nexters.doctor24.web;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.external.publicdata.invoker.PublicDataInvoker;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;
import me.nexters.doctor24.medical.pharmacy.model.mongo.Pharmacy;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyRepository;

// TODO 배치 작업 전 임시 더미 데이터 로드 클래스 임 나중에 지울 것임
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MockDataLoader implements ApplicationRunner {
	private final PublicDataInvoker publicdataInvoker;
	private final HospitalRepository hospitalRepository;
	private final PharmacyRepository pharmacyRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		PageResponse<HospitalRaw> hospitalResult = publicdataInvoker.getHospitalsByCityAndProvinceOrderBy(
			PageRequest.of(1, 10), "서울특별시", "강남구");

		hospitalResult.getContents().stream()
			.map(this::hospitalParser)
			.forEach(hospitalRepository::save);

		PageResponse<PharmacyRaw> pharmacyResult = publicdataInvoker.getPharmacyByCityAndProvinceOrderBy(
			PageRequest.of(1, 10), "서울특별시", "강남구");

		pharmacyResult.getContents().stream()
			.map(this::pharmacyParser)
			.forEach(pharmacyRepository::save);
	}

	// TODO Batch processor 에서 필요한 파싱 로직이므로 간소화 혹은 분리
	private Pharmacy pharmacyParser(PharmacyRaw pharmacyRaw) {
		List<Day> days = List.of(Day.of(DayOfWeek.MONDAY,
			LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayStart().substring(0, 2)),
				Integer.parseInt(pharmacyRaw.getMondayStart().substring(2, 4))),
			LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayClose().substring(0, 2)),
				Integer.parseInt(pharmacyRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.TUESDAY,
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.WEDNESDAY,
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.THURSDAY,
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.FRIDAY,
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.SATURDAY,
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.SUNDAY,
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(pharmacyRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(pharmacyRaw.getMondayClose().substring(2, 4))))
		);

		return Pharmacy.builder()
			.id(pharmacyRaw.getId())
			.name(pharmacyRaw.getName())
			.latitude(pharmacyRaw.getLatitude())
			.longitude(pharmacyRaw.getLongitude())
			.phone(pharmacyRaw.getPhone())
			.address(pharmacyRaw.getAddress())
			.days(days)
			.build();
	}

	private Hospital hospitalParser(HospitalRaw hospitalRaw) {
		List<Day> days = List.of(Day.of(DayOfWeek.MONDAY,
			LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
				Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
			LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
				Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.TUESDAY,
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.WEDNESDAY,
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.THURSDAY,
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.FRIDAY,
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.SATURDAY,
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4)))),

			Day.of(DayOfWeek.SUNDAY,
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayStart().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayStart().substring(2, 4))),
				LocalTime.of(Integer.parseInt(hospitalRaw.getMondayClose().substring(0, 2)),
					Integer.parseInt(hospitalRaw.getMondayClose().substring(2, 4))))
		);

		return Hospital.builder()
			.id(hospitalRaw.getId())
			.name(hospitalRaw.getName())
			.latitude(hospitalRaw.getLatitude())
			.longitude(hospitalRaw.getLongitude())
			.phone(hospitalRaw.getDutyTel1())
			.address(hospitalRaw.getDutyAddr())
			.days(days)
			.build();
	}
}
