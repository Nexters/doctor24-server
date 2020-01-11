package me.nexters.doctor24.web;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.external.publicdata.invoker.PublicDataInvoker;
import me.nexters.doctor24.medical.common.Day;
import me.nexters.doctor24.medical.hospital.model.HospitalRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;
import me.nexters.doctor24.medical.hospital.model.mongo.Location;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;

@SpringBootTest
public class Dataload {
	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private PublicDataInvoker publicdataInvoker;

	@Test
	void name() {
		PageResponse<HospitalRaw> hospitalResult1 = publicdataInvoker.getHospitalsByCityAndProvinceOrderBy(
			PageRequest.of(1, 10), "서울특별시", "강남구");

		hospitalResult1.getContents().stream()
			.map(this::hospitalParser)
			.forEach(hospitalRepository::save);
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
			.location(new Location("Point",
				new double[]{hospitalRaw.getLongitude(), hospitalRaw.getLatitude()}))
			.phone(hospitalRaw.getDutyTel1())
			.address(hospitalRaw.getDutyAddr())
			.days(days)
			.build();
	}
}
