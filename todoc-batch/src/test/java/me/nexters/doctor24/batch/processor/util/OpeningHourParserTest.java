package me.nexters.doctor24.batch.processor.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class OpeningHourParserTest {
    @ParameterizedTest
    @ValueSource(strings = {"2400", "2500", "2430", "2530"})
    void 운영시간_예외처리(String input) {
        LocalTime result = OpeningHourParser.parse(input);
        assertThat(result).isEqualTo(LocalTime.of(23, 59));
    }
}