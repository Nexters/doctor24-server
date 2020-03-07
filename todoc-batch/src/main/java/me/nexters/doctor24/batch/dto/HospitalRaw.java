package me.nexters.doctor24.batch.dto;

import lombok.Getter;
import me.nexters.doctor24.batch.dto.hospital.basic.HospitalBasicRaw;
import me.nexters.doctor24.batch.dto.hospital.detail.HospitalDetailRaw;

import java.util.List;

@Getter
public class HospitalRaw {
    private List<HospitalBasicRaw> hospitalBasicRaws;
    private List<HospitalDetailRaw> hospitalDetailRaws;

    public HospitalRaw(List<HospitalBasicRaw> hospitalBasicRaws,
                       List<HospitalDetailRaw> hospitalDetailRaws) {
        this.hospitalBasicRaws = hospitalBasicRaws;
        this.hospitalDetailRaws = hospitalDetailRaws;
    }
}
