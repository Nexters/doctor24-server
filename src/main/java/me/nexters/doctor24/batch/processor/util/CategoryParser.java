package me.nexters.doctor24.batch.processor.util;

import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryParser {
	public static List<String> parse(String input) {
		return List.of(input.split(","));
	}
}
