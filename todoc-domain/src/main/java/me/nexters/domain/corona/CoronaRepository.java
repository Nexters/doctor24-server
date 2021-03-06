package me.nexters.domain.corona;

import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author manki.kim
 */
public interface CoronaRepository extends ReactiveMongoRepository<CoronaHospital, String> {
	// 데이터가 몇개나 나올지는 모르지만.. limit 제한 처리 필요 filtering 고려해서 max 30
	Flux<CoronaHospital> findByLocationNear(Point point, Distance distance, Pageable pageable);
}
