package me.nexters.doctor24.medical.api.response;

import lombok.Value;

/**
 * @author manki.kim
 */
@Value(staticConstructor = "of")
public class FacilityResponse {
	private String medicalType;
	private String facilityName;
}
