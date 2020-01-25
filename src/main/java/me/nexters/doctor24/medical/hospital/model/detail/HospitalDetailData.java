package me.nexters.doctor24.medical.hospital.model.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HospitalDetailData {
	@JsonProperty("item")
	private HospitalDetailRaw hospitalDetailRaws;
}
