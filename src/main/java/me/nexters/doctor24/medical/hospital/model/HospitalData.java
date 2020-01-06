package me.nexters.doctor24.medical.hospital.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/**
 * @author manki.kim
 */
@Getter
@ToString
public class HospitalData {
	@JsonProperty("item")
	private List<HospitalRaw> hospitalRaws;
}
