package me.nexters.doctor24.invoker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import me.nexters.doctor24.external.publicdata.holiday.invoker.HolidayInquires;
import me.nexters.doctor24.external.publicdata.holiday.model.HolidayRaw;

/**
 * @author manki.kim
 */
@SpringBootTest
public class HolidayInquiresTest {

	@Autowired
	private HolidayInquires holidayInquires;

	@Test
	public void getHoliday() {
		List<HolidayRaw> holidayRaws = holidayInquires.getHolidayRaws(2020);
		assertFalse(CollectionUtils.isEmpty(holidayRaws));
	}
}
