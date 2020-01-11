package me.nexters.doctor24.medical.hospital.model.mongo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Location {
	private String type;
	private double[] coordinates;
}
