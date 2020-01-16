package me.nexters.doctor24.batch.processor.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressParser {
	/**
	 * 기획 상 주소의 길이가 길기 때문에 화면에 뿌리기 적절치 않아 괄호와 괄호 안에 있는 것들을 전부 제거
	 * @param input 원래 주소
	 * @return 괄가 제거된 주소
	 */
	public String parse(String input) {
		return input.replaceAll("\\(.*\\)", "").trim();
	}
}
