package me.nexters.doctor24.medical.aggregator.secure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.aggregator.MedicalAggregator;
import me.nexters.doctor24.medical.api.response.FacilityIndexResponse;
import me.nexters.doctor24.medical.api.response.FacilityResponse;
import me.nexters.doctor24.medical.api.type.MedicalType;
import me.nexters.domain.common.Day;
import me.nexters.domain.secure.SecureHospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecureAggregator implements MedicalAggregator {
    private static final double RADIUS = 15.0;

    private final SecureHospitalRepository secureHospitalRepository;

    @Override
    public MedicalType type() {
        return MedicalType.secure;
    }

    @Override
    public Mono<FacilityResponse> getFacilityBy(String facilityId) {
        return secureHospitalRepository.findById(facilityId)
                .map(FacilityResponse::fromSecureHospital);
    }

    @Override
    public Flux<FacilityIndexResponse> getFacilitiesFilteringByDay(double latitude, double longitude, double radiusRange, int inquiryCount, Day requestDay) {
        return secureHospitalRepository.findByLocationNear(new Point(longitude, latitude),
                new Distance(RADIUS, Metrics.KILOMETERS),
                PageRequest.of(0, inquiryCount))
                .filter(hospital -> hospital.isOpenWithoutTime(requestDay))
                .map(hospital -> FacilityIndexResponse.fromSecureHospital(hospital, requestDay));
    }

    @Override
    public Flux<FacilityIndexResponse> getFacilitiesFilteringByCategoryAndDay(double latitude, double longitude, double radiusRange, int inquiryCount, String category, Day requestDay) {
        throw new UnsupportedOperationException("not support category filtering about secure hospital type");
    }
}
