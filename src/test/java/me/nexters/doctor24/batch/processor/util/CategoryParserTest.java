package me.nexters.doctor24.batch.processor.util;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class CategoryParserTest {
	@Test
	void 카테고리_파싱() {
		String input = "외과,정형외과,비뇨기과";
		List<String> parse = CategoryParser.parse(input);
		assertThat(parse).contains("외과", "정형외과", "비뇨기과");
	}
}