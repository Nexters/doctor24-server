package me.nexters.doctor24.medical.hospital.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class HospitalRaw {
	private String dutyEmclsName;
	private int rnum;
	private String phpid;
	private String dutyEmcls;
	private String dutyInf;

	@JsonProperty("hpid")
	private String id;

	@JsonProperty("wgs84Lon")
	private double longitude;

	@JsonProperty("wgs84Lat")
	private double latitude;

	@JsonProperty("dutyName")
	private String name;

	@JsonProperty("dutyTel1")
	private String phone;

	@JsonProperty("dutyAddr")
	private String address;

	@JsonProperty("dutyDivNam")
	private String type;

	@JsonProperty("dutyTime1s")
	private String mondayStart;

	@JsonProperty("dutyTime1c")
	private String mondayClose;

	@JsonProperty("dutyTime2s")
	private String tuesdayStart;

	@JsonProperty("dutyTime2c")
	private String tuesdayClose;

	@JsonProperty("dutyTime3s")
	private String wednesdayStart;

	@JsonProperty("dutyTime3c")
	private String wednesdayClose;

	@JsonProperty("dutyTime4s")
	private String thursdayStart;

	@JsonProperty("dutyTime4c")
	private String thursdayClose;

	@JsonProperty("dutyTime5s")
	private String fridayStart;

	@JsonProperty("dutyTime5c")
	private String fridayClose;

	@JsonProperty("dutyTime6s")
	private String saturdayStart;

	@JsonProperty("dutyTime6c")
	private String saturdayClose;

	@JsonProperty("dutyTime7s")
	private String sundayStart;

	@JsonProperty("dutyTime7c")
	private String sundayClose;

	// 공휴일
	@JsonProperty("dutyTime8s")
	private String holidayStart;

	@JsonProperty("dutyTime8c")
	private String holidayClose;
}
