package me.nexters.doctor24.medical.secure.repository;

import me.nexters.doctor24.medical.secure.model.SecureHospital;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface SecureHospitalRepository extends ReactiveMongoRepository<SecureHospital, String> {
    Flux<SecureHospital> findByLocationNear(Point point, Distance distance, Pageable pageable);
}
