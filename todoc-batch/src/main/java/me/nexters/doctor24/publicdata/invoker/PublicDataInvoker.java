package me.nexters.doctor24.publicdata.invoker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.batch.dto.hospital.basic.HospitalBasicRaw;
import me.nexters.doctor24.batch.dto.hospital.basic.HospitalResponse;
import me.nexters.doctor24.batch.dto.hospital.detail.HospitalDetailRaw;
import me.nexters.doctor24.batch.dto.hospital.detail.HospitalDetailResponse;
import me.nexters.doctor24.batch.dto.pharmacy.PharmacyRaw;
import me.nexters.doctor24.batch.dto.pharmacy.PharmacyResponse;
import me.nexters.doctor24.batch.support.JacksonUtils;
import me.nexters.doctor24.batch.support.JsonParser;
import me.nexters.doctor24.page.PageRequest;
import me.nexters.doctor24.page.PageResponse;
import me.nexters.doctor24.publicdata.HospitalInquires;
import me.nexters.doctor24.publicdata.PharmacyInquires;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author manki.kim
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicDataInvoker implements HospitalInquires, PharmacyInquires {

	private final HospitalInvoker hospitalInvoker;
	private final PharmacyInvoker pharmacyInvoker;

	@Value("${hospital.key}")
	private String key;

	@Override
	public PageResponse<HospitalBasicRaw> getHospitalPage(PageRequest pageRequest) {
		try {
			String xmlResult = hospitalInvoker.getHospitals(key, pageRequest.getPageSafety(),
				pageRequest.getCount()).block();
			HospitalResponse response = toObjectFromResponse(xmlResult, HospitalResponse.class);

			return PageResponse.of(response.getHospitals(), pageRequest);
		} catch (Exception e) {
			log.warn("hospital page 요청 exception 발생 msg : {} ", e.getMessage());
			return null;
		}
	}

	@Override
	public Optional<HospitalDetailRaw> getHospitalDetailPage(String hospitalId) {
		try {
			String xmlResult = hospitalInvoker.getHospitalDetails(key, hospitalId).block();
			HospitalDetailResponse response = toObjectFromResponse(xmlResult, HospitalDetailResponse.class);
			return Optional.of(response.getHospitalDetail());
		} catch (Exception e) {
			log.info("Exception explicitly caught: " + e.getMessage());
			log.info("hospital detail invoke timeout (hospital ID: " + hospitalId + ")");
			return Optional.empty();
		}
	}

	@Override
	public PageResponse<HospitalBasicRaw> getHospitalsByCityAndProvinceOrderBy(PageRequest pageRequest, String city,
		String province) {
		String xmlResult = hospitalInvoker.getHospitalsByCityAndProvinceOrderBy(key, city, province, "NAME",
			pageRequest.getPageSafety(), pageRequest.getCount()).block();
		HospitalResponse response = toObjectFromResponse(xmlResult, HospitalResponse.class);

		return PageResponse.of(response.getHospitals(), pageRequest);
	}

	// 공공 데이터 호출 포맷이 어느정도 일정 하다면(xml -> response -> json -> pojo) template interface로 만들어서 사용 고려
	private <T> T toObjectFromResponse(String xml, Class<T> tClass) {
		JSONObject jsonResult = JsonParser.parse(xml).getJSONObject("response");
		return JacksonUtils.readValue(jsonResult.toString(), tClass)
			//.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
				// TODO spring web 의존성 제거
			.orElseThrow(() -> new RuntimeException("jsonResult parsing error"));
	}

	@Override
	public PageResponse<PharmacyRaw> getPharmacyPage(PageRequest pageRequest) {
		try {
			String xmlResult = pharmacyInvoker.getPharmacies(key, pageRequest.getPageSafety(),
				pageRequest.getCount()).block();
			PharmacyResponse response = toObjectFromResponse(xmlResult, PharmacyResponse.class);

			return PageResponse.of(response.getPharmacies(), pageRequest);
		} catch (Exception e) {
			// TODO exception 처리 (호출하는데서 처리하긴 함)
			return null;
		}
	}

	@Override
	public PageResponse<PharmacyRaw> getPharmacyByCityAndProvinceOrderBy(PageRequest pageRequest, String city,
																		 String province) {
		String xmlResult = pharmacyInvoker.getPharmaciesByCityAndProvinceOrderBy(key, city, province, "NAME",
			pageRequest.getPageSafety(), pageRequest.getCount()).block();
		PharmacyResponse response = toObjectFromResponse(xmlResult, PharmacyResponse.class);

		return PageResponse.of(response.getPharmacies(), pageRequest);
	}
}
