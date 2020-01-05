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
import me.nexters.doctor24.medical.hospital.api.HospitalApi;
import me.nexters.doctor24.medical.hospital.model.Hospital;
import me.nexters.doctor24.medical.hospital.model.HospitalResponse;
import me.nexters.doctor24.support.JacksonUtils;
import me.nexters.doctor24.support.JsonParser;

/**
 * @author manki.kim
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicdataInvoker implements HospitalApi {

	private final HospitalInvoker hospitalInvoker;

	@Value("${hospital.key}")
	private String key;

	@Override
	public PageResponse<Hospital> getHospitalPage(PageRequest pageRequest) {
		String xmlResult = hospitalInvoker.getHospitals(key, pageRequest.getPage(),
			pageRequest.getCount());
		HospitalResponse response = toObjectFromResponse(xmlResult, HospitalResponse.class);

		return PageResponse.of(response.getHospitals(), pageRequest);
	}

	private <T> T toObjectFromResponse(String xml, Class<T> tClass) {
		JSONObject jsonResult = JsonParser.parse(xml).getJSONObject("response");
		return JacksonUtils.readValue(jsonResult.toString(), tClass)
			.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
	}
}
