package me.nexters.doctor24.medical.pharmacy.repository;

import me.nexters.doctor24.common.page.PageRequest;
import me.nexters.doctor24.common.page.PageResponse;
import me.nexters.doctor24.medical.pharmacy.model.PharmacyRaw;

public interface PharmacyInquires {
	PageResponse<PharmacyRaw> getPharmacyPage(PageRequest pageRequest);

	PageResponse<PharmacyRaw> getPharmacyByCityAndProvinceOrderBy(PageRequest pageRequest, String city, String province);
}
