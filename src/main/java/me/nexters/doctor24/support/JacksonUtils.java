package me.nexters.doctor24.support;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public final class JacksonUtils {
	private static final ObjectMapper MAPPER = new ObjectMapper().disable(
		DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	public static <T> Optional<T> readValue(final String json, Class<T> tClass) {
		if (json == null) {
			return Optional.empty();
		}

		try {
			return Optional.ofNullable(MAPPER.readValue(json, tClass));
		} catch (Exception e) {
			log.error(e.getMessage(), json, e);
			return Optional.empty();
		}
	}
}
