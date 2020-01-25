package me.nexters.doctor24.medical.hospital.repository;

import java.util.Optional;

import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;

/**
 * @author manki.kim
 */
public interface HospitalInquires {
	// 지금은 따로 정렬 기준을 두고 있지 않지만 공공 데이터 조회시 정렬 기준을 주고 싶으면 Sort 클래스 생성후 함께 파라미터로 넘기는 것도 고려
	PageResponse<HospitalBasicRaw> getHospitalPage(PageRequest pageRequest);

	Optional<HospitalDetailRaw> getHospitalDetailPage(String hospitalId);

	PageResponse<HospitalBasicRaw> getHospitalsByCityAndProvinceOrderBy(PageRequest pageRequest, String city, String province);
}
