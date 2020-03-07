package me.nexters.doctor24.publicdata;

import me.nexters.doctor24.batch.dto.pharmacy.PharmacyRaw;
import me.nexters.doctor24.page.PageRequest;
import me.nexters.doctor24.page.PageResponse;

public interface PharmacyInquires {
	PageResponse<PharmacyRaw> getPharmacyPage(PageRequest pageRequest);

	PageResponse<PharmacyRaw> getPharmacyByCityAndProvinceOrderBy(PageRequest pageRequest, String city, String province);
}
