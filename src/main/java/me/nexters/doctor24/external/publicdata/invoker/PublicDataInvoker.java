package me.nexters.doctor24.external.publicdata.invoker;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import me.nexters.doctor24.external.publicdata.holiday.invoker.HolidayInquires;
import me.nexters.doctor24.external.publicdata.holiday.invoker.HolidayInvoker;
import me.nexters.doctor24.external.publicdata.holiday.model.HolidayRaw;
import me.nexters.doctor24.external.publicdata.holiday.model.HolidayResponse;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalResponse;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailResponse;
import me.nexters.doctor24.medical.hospital.repository.HospitalInquires;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyResponse;
import me.nexters.doctor24.medical.pharmacy.repository.PharmacyInquires;
import me.nexters.doctor24.support.JacksonUtils;
import me.nexters.doctor24.support.JsonParser;

/**
 * @author manki.kim
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicDataInvoker implements HospitalInquires, PharmacyInquires, HolidayInquires {

	private final HospitalInvoker hospitalInvoker;
	private final PharmacyInvoker pharmacyInvoker;
	private final HolidayInvoker holidayInvoker;

	@Value("${hospital.key}")
	private String key;

	@Value("${holiday.key}")
	private String holidayKey;

	@Override
	public PageResponse<HospitalBasicRaw> getHospitalPage(PageRequest pageRequest) {
		try {
			String xmlResult = hospitalInvoker.getHospitals(key, pageRequest.getPageSafety(),
				pageRequest.getCount()).block();
			HospitalResponse response = toObjectFromResponse(xmlResult, HospitalResponse.class);

			return PageResponse.of(response.getHospitals(), pageRequest);
		} catch (Exception e) {
			return PageResponse.of(Collections.emptyList(), PageRequest.of(pageRequest.getPageSafety() - 1,
				pageRequest.getCount()));
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
			.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
	}

	@Override
	public PageResponse<PharmacyRaw> getPharmacyPage(PageRequest pageRequest) {
		String xmlResult = pharmacyInvoker.getPharmacies(key, pageRequest.getPageSafety(),
			pageRequest.getCount()).block();
		PharmacyResponse response = toObjectFromResponse(xmlResult, PharmacyResponse.class);

		return PageResponse.of(response.getPharmacies(), pageRequest);
	}

	@Override
	public PageResponse<PharmacyRaw> getPharmacyByCityAndProvinceOrderBy(PageRequest pageRequest, String city,
		String province) {
		String xmlResult = pharmacyInvoker.getPharmaciesByCityAndProvinceOrderBy(key, city, province, "NAME",
			pageRequest.getPageSafety(), pageRequest.getCount()).block();
		PharmacyResponse response = toObjectFromResponse(xmlResult, PharmacyResponse.class);

		return PageResponse.of(response.getPharmacies(), pageRequest);
	}

	@Override
	public List<HolidayRaw> getHolidayRaws(int year) {
		String xmlResult = holidayInvoker.getHoliday(holidayKey, year).block();
		HolidayResponse response = toObjectFromResponse(xmlResult, HolidayResponse.class);

		return response.getHolidayRaws();
	}
}
