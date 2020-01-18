package me.nexters.doctor24.medical.hospital.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class HospitalDetailRaw {
	@JsonProperty("dgidIdName")
	private String categories;
}
