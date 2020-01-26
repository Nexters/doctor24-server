package me.nexters.doctor24.medical.hospital.model.detail;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ToString
public class HospitalDetailBody {
	private HospitalDetailData items;
}