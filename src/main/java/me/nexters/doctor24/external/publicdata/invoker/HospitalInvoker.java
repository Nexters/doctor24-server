package me.nexters.doctor24.external.publicdata.invoker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
@ReactiveFeignClient(name = "hospital", url = "http://apis.data.go.kr/B552657")
public interface HospitalInvoker {
	@GetMapping("/HsptlAsembySearchService/getHsptlMdcncFullDown")
	Mono<String> getHospitals(@RequestParam("serviceKey") String serviceKey,
		@RequestParam("pageNo") int pageNo, @RequestParam("numOfRows") int numOfRows);
}
