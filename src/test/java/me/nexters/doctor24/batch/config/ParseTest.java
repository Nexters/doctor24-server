package me.nexters.doctor24.batch.config;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ParseTest {
	@Test
	void name() {
		List<String> list = List.of("치과", "정형외과");
		System.out.println(list.toString());
	}
}
