package me.nexters.domain.pharmacy;

import me.nexters.domain.common.Day;
import org.junit.Ignore;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class PharmacyRepositoryTest {
    // 이거 하려면 flapdoodle 의 embedded mongo 테스트 추가해서 하면 됨
    private PharmacyRepository pharmacyRepository;

    @Ignore
    void 약국_도메인_저장() {
        List<Day> days = List.of(
                Day.of(Day.DayType.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.TUESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.THURSDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.FRIDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.SATURDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.SUNDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
        );

        Pharmacy pharmacy = Pharmacy.builder()
                .id("C1800232")
                .days(days)
                .location(new GeoJsonPoint(127.2353413861, 36.5195854195))
                .name("고운길온누리약국")
                .phone("070-7716-7577")
                .build();

        Pharmacy saved = pharmacyRepository.save(pharmacy).block();

        assertThat(pharmacy).isEqualTo(saved);
    }
}