package me.nexters.doctor24.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.nexters.doctor24.medical.holiday.HolidayManager;

/**
 * @author manki.kim
 */
@SpringBootTest
public class HolidayManagerTest {
	@Autowired
	private HolidayManager holidayManager;

	@Nested
	class True {
		@Test
		public void isHoliday() {
			boolean result = holidayManager.isHoliday(LocalDate.of(2020, 1, 27));
			assertTrue(result);
		}
	}

	@Nested
	class False {
		@Test
		public void isHoliday() {
			boolean result = holidayManager.isHoliday(LocalDate.of(2020, 1, 28));
			assertFalse(result);
		}
	}
}
