package me.nexters.domain.hospital;

import lombok.extern.slf4j.Slf4j;
import me.nexters.domain.common.Day;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Slf4j
@DataMongoTest
class HospitalRepositoryTest {

    // 이거 하려면 flapdoodle 의 embedded mongo 테스트 추가해서 하면 됨
    private HospitalRepository hospitalRepository;

    @BeforeEach
    void 병원_도메인_저장() {
        List<Day> days = List.of(
                Day.of(Day.DayType.MONDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.TUESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.THURSDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.FRIDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.SATURDAY, LocalTime.of(9, 0), LocalTime.of(18, 0)),
                Day.of(Day.DayType.SUNDAY, LocalTime.of(9, 0), LocalTime.of(18, 0))
        );

        GeoJsonPoint location = new GeoJsonPoint(127.0395873429168, 37.485612179925724);
        Hospital hospital = Hospital.builder()
                .name("토닥병원")
                .id("B1100027")
                .hospitalType(HospitalType.find("한방병원"))
                .days(days)
                .address("서울특별시 강남구 봉은사로 612 (삼성동)")
                .phone("02-2222-4888")
                .location(location)
                .rowWriteDate(LocalDateTime.now())
                .build();

        Hospital saved = hospitalRepository.save(hospital).block();

        assertThat(hospital, is(saved));
    }

    @Test
    void 근접_지점_검색() {
        Point point = new Point(127.0395873429168, 37.485612179925724);
        Distance distance = new Distance(0.5, Metrics.KILOMETERS);
        hospitalRepository.findByLocationNear(point, distance,
                PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "location")))
                .subscribe(System.out::println);
    }

    @Test
    void polygon_type_검색() {
        Point x = new Point(127.025365, 37.498821);
        Point y = new Point(127.028380, 37.498821);
        Point z = new Point(127.028380, 37.501213);
        Point w = new Point(127.025365, 37.501213);
        Point other = new Point(127.025365, 37.498821);
        Polygon polygon = new Polygon(x, y, z, w, other);
        hospitalRepository.findByLocationWithin(polygon,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "location"))).collectList().block()
                .forEach(System.out::println);
    }

    @Test
    void 강남역_병원_카테고리_필터링_조회() {
        Point point = new Point(127.027599, 37.497971);
        Distance distance = new Distance(0.5, Metrics.KILOMETERS);
        Flux<Hospital> results =
                hospitalRepository.findByLocationNearAndCategories(point, distance, "내과",
                        PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "location")));
        List<Hospital> hospitals = results.collectList().block();
        assertTrue(!CollectionUtils.isEmpty(hospitals));
    }
}