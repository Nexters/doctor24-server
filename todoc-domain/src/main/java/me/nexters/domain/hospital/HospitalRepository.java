package me.nexters.domain.hospital;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface HospitalRepository extends ReactiveMongoRepository<Hospital, String> {
	// 데이터가 몇개나 나올지는 모르지만.. limit 제한 처리 필요 filtering 고려해서 max 30
	Flux<Hospital> findByLocationNear(Point point, Distance distance, Pageable pageable);

	Flux<Hospital> findByLocationNearAndCategories(Point point, Distance distance, String category, Pageable pageable);

	Flux<Hospital> findByLocationWithin(Polygon polygon, Pageable pageable);
}
