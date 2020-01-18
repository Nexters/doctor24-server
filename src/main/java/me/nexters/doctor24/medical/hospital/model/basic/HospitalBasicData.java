package me.nexters.doctor24.medical.hospital.model.basic;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author manki.kim
 */
@Getter
@ToString
public class HospitalBasicData {
	@JsonProperty("item")
	private List<HospitalBasicRaw> hospitalBasicRaws;
}
