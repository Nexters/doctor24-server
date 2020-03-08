package me.nexters.doctor24.medical.external.holiday;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.external.holiday.dto.HolidayRaw;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author manki.kim
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HolidayManagerFactory implements FactoryBean<HolidayManager> {
    private final HolidayInquires holidayInquires;

    @Override
    public HolidayManager getObject() {
        List<HolidayRaw> holidayRaws = holidayInquires.getHolidayRaws(LocalDate.now().getYear());
        Set<LocalDate> holidaySet = holidayRaws.stream()
                .filter(holidayRaw -> holidayRaw.getIsHoliday().equals("Y"))
                .map(HolidayRaw::getDate)
                .map(String::valueOf)
                .map(date -> LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")))
                .collect(Collectors.toUnmodifiableSet());
        return new HolidayManager(holidaySet);
    }

    @Override
    public Class<?> getObjectType() {
        return HolidayManager.class;
    }
}
