package me.nexters.doctor24.medical.external.holiday;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import me.nexters.doctor24.medical.external.holiday.dto.HolidayRaw;

/**
 * @author manki.kim
 */
@SpringBootTest
public class HolidayInquiresTest {

	@Autowired
	private HolidayInquires holidayInquires;

	@Test
	public void getHoliday() {
		List<HolidayRaw> holidayRaws = holidayInquires.getHolidayRaws(LocalDate.now().getYear(),
			LocalDate.now().getMonthValue());
		assertFalse(CollectionUtils.isEmpty(holidayRaws));
	}
}
