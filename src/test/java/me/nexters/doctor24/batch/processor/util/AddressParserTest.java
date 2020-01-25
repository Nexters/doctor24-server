package me.nexters.doctor24.batch.processor.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AddressParserTest {
	@ParameterizedTest
	@ValueSource(strings = {"경기도 수원시 팔달구 매산로 124 (교동)",
		"경기도 수원시 팔달구 화산로 62, 201-3호 (화서동)",
		"서울특별시 강남구 테헤란로 513 (삼성동, B1일부,4,9,10층,테헤란로81길10,B1일부,삼성로92길29,3,4)",
		"부산광역시 영도구 절영로 30 (대교동1가)",
		"부산광역시 서구 옥천로130번길 38"
	})
	void 주소_변환_괄호제거(String input) {
		String result = AddressParser.parse(input);
		assertFalse(result.contains(")"));
		assertFalse(result.contains("("));
		System.out.println(result);
	}
}