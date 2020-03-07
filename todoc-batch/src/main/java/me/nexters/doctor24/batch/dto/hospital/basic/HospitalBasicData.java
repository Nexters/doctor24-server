package me.nexters.doctor24.batch.dto.hospital.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * @author manki.kim
 */
@Getter
@ToString
public class HospitalBasicData {
	@JsonProperty("item")
	private List<HospitalBasicRaw> hospitalBasicRaws;
}
