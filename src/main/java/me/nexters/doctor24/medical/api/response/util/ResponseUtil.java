package me.nexters.doctor24.medical.api.response.util;

import java.util.Collections;
import java.util.List;

import lombok.experimental.UtilityClass;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

/**
 * DTO 에서 도메인을 그대로 가져다 쓰기 때문에 도메인 값을 바꿀수도 없고
 * 중간 DTO 만들자니 귀찮은 결과 프런트에 내려주는 DTO 에서 파싱
 */
@UtilityClass
public class ResponseUtil {
	public static List<String> filterCategories(Hospital hospital) {
		List<String> categories = hospital.getCategories();
		return checkCategoriesNotExist(categories) ? Collections.emptyList() : categories;
	}

	private static boolean checkCategoriesNotExist(List<String> categories) {
		return categories.isEmpty() || categories.get(0).equals("");
	}
}
