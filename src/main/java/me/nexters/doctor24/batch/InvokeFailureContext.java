package me.nexters.doctor24.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvokeFailureContext {
	private static final List<String> FAILED_HOSPITAL_IDS = new CopyOnWriteArrayList<>();

	private InvokeFailureContext() {
	}

	public static void add(String id) {
		FAILED_HOSPITAL_IDS.add(id);
	}

	public static boolean isEmpty() {
		return FAILED_HOSPITAL_IDS.isEmpty();
	}

	public static List<String> getFailedHospitalIds() {
		return new ArrayList<>(FAILED_HOSPITAL_IDS);
	}

	public static int size() {
		return FAILED_HOSPITAL_IDS.size();
	}

	public static void clear() {
		FAILED_HOSPITAL_IDS.clear();
	}
}
