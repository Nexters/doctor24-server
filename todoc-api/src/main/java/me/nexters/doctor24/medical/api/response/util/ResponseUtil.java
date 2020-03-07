package me.nexters.doctor24.medical.api.response.util;

import lombok.experimental.UtilityClass;
import me.nexters.domain.hospital.Hospital;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DTO 에서 도메인을 그대로 가져다 쓰기 때문에 도메인 값을 바꿀수도 없고
 * 중간 DTO 만들자니 귀찮은 결과 프런트에 내려주는 DTO 에서 파싱
 */
@UtilityClass
public class ResponseUtil {
	private static final Map<String, String> PLACES = new HashMap<>();
	private static final String REGEX = "서울특별시|부산광역시|대구광역시|인천광역시|광주광역시|대전광역시|울산광역시|"
		+ "세종특별자치시|충청북도|충청남도|전라북도|전라남도|경상북도|경상남도|제주특별자치도";
	private static final Pattern pattern = Pattern.compile(REGEX);

	static {
		PLACES.put("서울특별시", "서울시");
		PLACES.put("부산광역시", "인천시");
		PLACES.put("대구광역시", "대구시");
		PLACES.put("인천광역시", "인천시");
		PLACES.put("광주광역시", "광주시");
		PLACES.put("대전광역시", "대전시");
		PLACES.put("울산광역시", "울산시");
		PLACES.put("세종특별자치시", "세종시");
		PLACES.put("충청북도", "충북");
		PLACES.put("충청남도", "충남");
		PLACES.put("전라북도", "전북");
		PLACES.put("전라남도", "전남");
		PLACES.put("경상북도", "경북");
		PLACES.put("경상남도", "경남");
		PLACES.put("제주특별자치도", "제주시");
	}

	public static List<String> filterCategories(Hospital hospital) {
		List<String> categories = hospital.getCategories();
		return checkCategoriesNotExist(categories) ? Collections.emptyList() : categories;
	}

	private static boolean checkCategoriesNotExist(List<String> categories) {
		return categories.isEmpty() || categories.get(0).equals("");
	}

	public static String filterAddress(String address) {
		StringBuffer sb = new StringBuffer();
		Matcher matcher = pattern.matcher(address);
		while (matcher.find()) {
			matcher.appendReplacement(sb, PLACES.get(matcher.group()));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
