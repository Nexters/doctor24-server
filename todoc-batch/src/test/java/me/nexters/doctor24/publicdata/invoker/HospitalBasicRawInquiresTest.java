package me.nexters.doctor24.publicdata.invoker;

import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.dto.hospital.basic.HospitalBasicRaw;
import me.nexters.doctor24.batch.dto.hospital.detail.HospitalDetailRaw;
import me.nexters.doctor24.batch.processor.util.OpeningHourParser;
import me.nexters.doctor24.page.PageRequest;
import me.nexters.doctor24.page.PageResponse;
import me.nexters.doctor24.publicdata.HospitalInquires;
import me.nexters.domain.common.Day;
import me.nexters.domain.hospital.Hospital;
import me.nexters.domain.hospital.HospitalRepository;
import me.nexters.domain.hospital.HospitalType;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
@SpringBootTest
class HospitalBasicRawInquiresTest {
    @Autowired
    private HospitalInquires hospitalInquires;

    @Autowired
    private HospitalRepository hospitalRepository;

    private int migrationCount = 0;

    @Test
    void 전국_병원_인덱스() {
        PageResponse<HospitalBasicRaw> hospitalPage =
                hospitalInquires.getHospitalPage(PageRequest.of(2, 100));
        assertThat(hospitalPage.getContents().size(), is(100));
    }

    @Test
    void 병원_상세_조회() {
        HospitalDetailRaw hospitalDetailRaw = hospitalInquires.getHospitalDetailPage("A1119600").orElseThrow(RuntimeException::new);

        assertThat(hospitalDetailRaw.getCategories(), is("내과,비뇨기과,성형외과,소아청소년과,피부과"));
        System.out.println(hospitalDetailRaw);
    }

    @Test
    void 강남구_병원_호출() {
        PageResponse<HospitalBasicRaw> hospitalsByCityAndProvinceOrderBy =
                hospitalInquires.getHospitalsByCityAndProvinceOrderBy(
                        PageRequest.of(1, 100), "서울특별시", "강남구");

        assertThat(hospitalsByCityAndProvinceOrderBy.getContents().size(), is(100));
    }

    @Ignore
    void 병원_전체_목록_마이그레이션() {
        PageResponse<HospitalBasicRaw> hospitalPage =
                hospitalInquires.getHospitalPage(PageRequest.of(1, 250));
        List<HospitalBasicRaw> hospitals = new ArrayList<>(hospitalPage.getContents());
        while (hospitalPage.hasNext()) {
            hospitalPage =
                    hospitalInquires.getHospitalPage(PageRequest.of(hospitalPage.getNextPage(), 250));
            hospitals.addAll(hospitalPage.getContents());
        }

        System.out.println(hospitals.size());

        System.out.println("----- start migration -----");

        hospitals.parallelStream()
                .map(this::hospitalParser)
                .forEach(item -> {
                    if (Objects.nonNull(item)) {
                        hospitalRepository.save(item).block();
                        migrationCount++;
                    }
                });

        System.out.println("success migration count " + migrationCount);
    }

    private Hospital hospitalParser(HospitalBasicRaw hospitalBasicRaw) {
        try {
            List<Day> days = new ArrayList<>();
            if (Objects.nonNull(hospitalBasicRaw.getMondayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getMondayClose())) {
                days.add(Day.of(Day.DayType.MONDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getMondayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getMondayClose())));
            }

            if (Objects.nonNull(hospitalBasicRaw.getTuesdayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getTuesdayClose())) {
                days.add(Day.of(Day.DayType.TUESDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getTuesdayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getTuesdayClose())));
            }

            if (Objects.nonNull(hospitalBasicRaw.getWednesdayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getWednesdayClose())) {
                days.add(Day.of(Day.DayType.WEDNESDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getWednesdayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getWednesdayClose())));
            }

            if (Objects.nonNull(hospitalBasicRaw.getThursdayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getThursdayClose())) {
                days.add(Day.of(Day.DayType.THURSDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getThursdayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getThursdayClose())));
            }

            if (Objects.nonNull(hospitalBasicRaw.getFridayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getFridayClose())) {
                days.add(Day.of(Day.DayType.FRIDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getFridayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getFridayClose())));
            }

            if (Objects.nonNull(hospitalBasicRaw.getSaturdayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getSaturdayClose())) {
                days.add(Day.of(Day.DayType.SATURDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getSaturdayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getSaturdayClose())));
            }

            if (Objects.nonNull(hospitalBasicRaw.getSundayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getSundayClose())) {
                days.add(Day.of(Day.DayType.SUNDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getSundayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getSundayClose())));
            }

            if (Objects.nonNull(hospitalBasicRaw.getHolidayStart()) && Objects.nonNull(
                    hospitalBasicRaw.getHolidayClose())) {
                days.add(Day.of(Day.DayType.HOLIDAY,
                        OpeningHourParser.parse(hospitalBasicRaw.getHolidayStart()),
                        OpeningHourParser.parse(hospitalBasicRaw.getHolidayClose())));
            }

            return Hospital.builder()
                    .id(hospitalBasicRaw.getId())
                    .name(hospitalBasicRaw.getName())
                    .location(new GeoJsonPoint(hospitalBasicRaw.getLongitude(), hospitalBasicRaw.getLatitude()))
                    .phone(hospitalBasicRaw.getPhone())
                    .address(hospitalBasicRaw.getAddress())
                    .hospitalType(HospitalType.find(hospitalBasicRaw.getType()))
                    .days(days)
                    .build();
        } catch (Exception e) {
            log.error("[FAIL TO MIGRATION] {} {}", hospitalBasicRaw.getId(), e.getMessage());
            return null;
        }
    }
}