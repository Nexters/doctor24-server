package me.nexters.doctor24.medical.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HospitalResponse {
    @JsonProperty("body")
    private Body result;

}
