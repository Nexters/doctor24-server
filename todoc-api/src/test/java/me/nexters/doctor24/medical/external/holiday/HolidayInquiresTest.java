package me.nexters.doctor24.medical.external.holiday;

import me.nexters.doctor24.medical.external.holiday.dto.HolidayRaw;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertFalse;

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
