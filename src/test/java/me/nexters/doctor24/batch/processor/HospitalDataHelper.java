package me.nexters.doctor24.batch.processor;

import me.nexters.doctor24.medical.hospital.model.HospitalRaw;

public class HospitalDataHelper {
	public static HospitalRaw createHospitalRaw() {
		HospitalRaw hospitalRaw = new HospitalRaw();
		hospitalRaw.setId("A1100141");
		hospitalRaw.setLongitude(127.0395873429168);
		hospitalRaw.setLatitude(37.485612179925724);
		hospitalRaw.setName("넥터병원");
		hospitalRaw.setAddress("서울시 넥터로 넥터골목 2번지");
		hospitalRaw.setPhone("010-1111-1111");
		hospitalRaw.setType("무슨벼원");
		hospitalRaw.setMondayStart("0900");
		hospitalRaw.setMondayClose("1800");
		hospitalRaw.setTuesdayStart("0900");
		hospitalRaw.setTuesdayClose("1800");
		hospitalRaw.setWednesdayStart("0900");
		hospitalRaw.setWednesdayClose("1800");
		hospitalRaw.setThursdayStart("0900");
		hospitalRaw.setThursdayClose("1800");
		hospitalRaw.setFridayStart("0900");
		hospitalRaw.setFridayClose("1800");
		hospitalRaw.setSaturdayStart("0900");
		hospitalRaw.setSaturdayClose("1800");
		hospitalRaw.setSundayStart("0000");
		hospitalRaw.setSundayClose("2400");
		hospitalRaw.setHolidayStart("0000");
		hospitalRaw.setHolidayClose("2400");
		return hospitalRaw;
	}
}
