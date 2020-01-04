package me.nexters.doctor24.medical.hospital.api;

import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.medical.hospital.model.Hospital;

/**
 * @author manki.kim
 */
public interface HospitalApi {
	PageResponse<Hospital> getHospitalPage(PageRequest pageRequest);
}
