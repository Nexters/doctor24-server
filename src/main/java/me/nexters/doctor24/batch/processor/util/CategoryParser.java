package me.nexters.doctor24.batch.processor.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryParser {
	public static List<String> parse(String input) {
		if (input == null) {
			return Collections.emptyList();
		}
		return Arrays.stream(input.split(","))
			.map(String::trim)
			.collect(Collectors.toList());
	}

	public static String toRaw(List<String> categories) {
		if (categories == null) {
			return "";
		}
		return categories.toString().replaceAll("\\[", "").replaceAll("\\]", "");
	}
}
