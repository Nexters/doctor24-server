package me.nexters.doctor24.support;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DistanceUtils {
	public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

	public int calculateDistanceInKilometer(double lat1, double long1, double lat2, double long2) {
		double latDistance = Math.toRadians(lat1 - lat2);
		double lngDistance = Math.toRadians(long1 - long2);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return (int)(Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
	}
}
