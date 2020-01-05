package me.nexters.doctor24.medical.hospital.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
//TODO 아래 필드들 적절하게 네이밍 변경 필요
public class Hospital {
	private String dutyEmclsName;
	private int rnum;
	private String hpid;
	private String phpid;
	private double wgs84Lon;
	private double wgs84Lat;
	private String dutyEmcls;
	private String dutyTel1;
	private String dutyAddr;
	private String dutyName;
	private String dutyInf;
	private String dutyTime1s;
	private String dutyTime1c;
	private String dutyTime2s;
	private String dutyTime2c;
	private String dutyTime3s;
	private String dutyTime3c;
	private String dutyTime4s;
	private String dutyTime4c;
	private String dutyTime5s;
	private String dutyTime5c;
	private String dutyTime6s;
	private String dutyTime6c;
	private String dutyTime7s;
	private String dutyTime7c;
	//공휴일 시간
	private String dutyTime8s;
	private String dutyTime8c;
}
