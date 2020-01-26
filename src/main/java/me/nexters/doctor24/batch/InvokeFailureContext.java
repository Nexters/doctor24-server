package me.nexters.doctor24.batch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.nexters.doctor24.medical.hospital.model.mongo.Hospital;

public class InvokeFailureContext {
	private static final Map<String, Hospital> FAILED_HOSPITALS = new ConcurrentHashMap<>();

	private InvokeFailureContext() {
	}

	public static void add(String id, Hospital hospital) {
		FAILED_HOSPITALS.put(id, hospital);
	}

	public static boolean isEmpty() {
		return FAILED_HOSPITALS.isEmpty();
	}

	public static Map<String, Hospital> getFailedHospitals() {
		return Map.copyOf(FAILED_HOSPITALS);
	}

	public static int size() {
		return FAILED_HOSPITALS.size();
	}

	public static void clear() {
		FAILED_HOSPITALS.clear();
	}
}
