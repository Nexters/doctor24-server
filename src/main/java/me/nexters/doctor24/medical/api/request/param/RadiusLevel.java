package me.nexters.doctor24.medical.api.request.param;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author manki.kim
 */
@Getter
@AllArgsConstructor
public enum RadiusLevel {
	LEVEL1(1, 1, 150),
	LEVEL2(2, 2, 150);

	private final int level;
	private final double rangeKM;
	private final int inquiryCount;

	private static final Map<Integer, RadiusLevel> LEVEL_HOLDER = Stream.of(RadiusLevel.values())
		.collect(Collectors.toMap(RadiusLevel::getLevel, Function.identity()));

	public static RadiusLevel getBy(int level) {
		return Optional.ofNullable(LEVEL_HOLDER.get(level))
			.orElse(LEVEL1);
	}
}
