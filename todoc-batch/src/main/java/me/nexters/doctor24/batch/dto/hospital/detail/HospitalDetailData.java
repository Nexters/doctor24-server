package me.nexters.doctor24.batch.dto.hospital.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
public class HospitalDetailData {
	@JsonProperty("item")
	private HospitalDetailRaw hospitalDetailRaws;
}
