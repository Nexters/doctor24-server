package me.nexters.doctor24.support;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OpeningHourParserTest {
	@ParameterizedTest
	@ValueSource(strings = {"2400", "2500", "2430", "2530"})
	void name(String input) {
		LocalTime result = OpeningHourParser.parse(input);
		assertThat(result).isEqualTo(LocalTime.of(23, 59));
	}
}