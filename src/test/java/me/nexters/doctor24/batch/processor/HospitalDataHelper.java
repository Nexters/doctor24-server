package me.nexters.doctor24.batch.processor;

import me.nexters.doctor24.medical.hospital.model.basic.HospitalBasicRaw;
import me.nexters.doctor24.medical.hospital.model.detail.HospitalDetailRaw;
import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

public class HospitalDataHelper {
	public static HospitalBasicRaw createHospitalBasicRaw() {
		HospitalBasicRaw hospitalBasicRaw = new HospitalBasicRaw();
		hospitalBasicRaw.setId("A1100141");
		hospitalBasicRaw.setLongitude(127.0395873429168);
		hospitalBasicRaw.setLatitude(37.485612179925724);
		hospitalBasicRaw.setName("넥터병원");
		hospitalBasicRaw.setAddress("서울시 넥터로 넥터골목 2번지");
		hospitalBasicRaw.setPhone("010-1111-1111");
		hospitalBasicRaw.setType("무슨벼원");
		hospitalBasicRaw.setMondayStart("0900");
		hospitalBasicRaw.setMondayClose("1800");
		hospitalBasicRaw.setTuesdayStart("0900");
		hospitalBasicRaw.setTuesdayClose("1800");
		hospitalBasicRaw.setWednesdayStart("0900");
		hospitalBasicRaw.setWednesdayClose("1800");
		hospitalBasicRaw.setThursdayStart("0900");
		hospitalBasicRaw.setThursdayClose("1800");
		hospitalBasicRaw.setFridayStart("0900");
		hospitalBasicRaw.setFridayClose("1800");
		hospitalBasicRaw.setSaturdayStart("0900");
		hospitalBasicRaw.setSaturdayClose("1800");
		hospitalBasicRaw.setSundayStart("0000");
		hospitalBasicRaw.setSundayClose("2400");
		hospitalBasicRaw.setHolidayStart("0000");
		hospitalBasicRaw.setHolidayClose("2400");
		return hospitalBasicRaw;
	}

	public static HospitalDetailRaw createHospitalDetailRaw() {
		HospitalDetailRaw hospitalDetailRaw = new HospitalDetailRaw();
		hospitalDetailRaw.setId("A1100141");
		hospitalDetailRaw.setCategories("내과,비뇨기과,성형외과,소아청소년과,피부과");
		return hospitalDetailRaw;
	}

	public static Hospital createHospitalWithoutCategories() {
		return Hospital.builder()
			.id("A1100141")
			.build();
	}
}
