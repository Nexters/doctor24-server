package me.nexters.doctor24.external.publicdata.invoker;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.medical.hospital.model.Hospital;
import me.nexters.doctor24.medical.hospital.model.HospitalResponse;
import me.nexters.doctor24.medical.hospital.repository.HospitalRepository;
import me.nexters.doctor24.support.JacksonUtils;
import me.nexters.doctor24.support.JsonParser;

/**
 * @author manki.kim
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicdataInvoker implements HospitalRepository {

	private final HospitalInvoker hospitalInvoker;

	@Value("${hospital.key}")
	private String key;

	@Override
	public PageResponse<Hospital> getHospitalPage(PageRequest pageRequest) {
		String xmlResult = hospitalInvoker.getHospitals(key, pageRequest.getPageSafety(),
			pageRequest.getCount());
		HospitalResponse response = toObjectFromResponse(xmlResult, HospitalResponse.class);

		return PageResponse.of(response.getHospitals(), pageRequest);
	}

	// 공공 데이터 호출 포맷이 어느정도 일정 하다면(xml -> response -> json -> pojo) template interface로 만들어서 사용 고려
	private <T> T toObjectFromResponse(String xml, Class<T> tClass) {
		JSONObject jsonResult = JsonParser.parse(xml).getJSONObject("response");
		return JacksonUtils.readValue(jsonResult.toString(), tClass)
			.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
	}
}
