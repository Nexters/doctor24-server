package me.nexters.doctor24.support;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DistanceUtilsTest {
	@Test
	void 두_점_사이의_거리_계산() {
		int result = DistanceUtils.calculateDistanceInKilometer(37.39730511981593, 126.92308216357338,
			37.485612179925724, 127.0395873429168);
		assertThat(result).isEqualTo(14);
	}
}