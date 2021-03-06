package me.nexters.domain.pharmacy;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PharmacyRepository extends ReactiveMongoRepository<Pharmacy, String> {
	Flux<Pharmacy> findByLocationNear(Point point, Distance distance, Pageable pageable);

	Flux<Pharmacy> findByLocationWithin(Polygon polygon, Pageable pageable);
}
