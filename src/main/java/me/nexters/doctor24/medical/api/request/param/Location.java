package me.nexters.doctor24.medical.api.request.param;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author manki.kim
 */
@Data
public class Location {
	@NotNull
	private String latitude;
	@NotNull
	private String longitude;
	private int radiusMeter = 500;
}
