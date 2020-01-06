package me.nexters.doctor24.external.publicdata.invoker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "pharmacy", url = "http://apis.data.go.kr/B552657")
public interface PharmacyInvoker {
	@GetMapping("/ErmctInsttInfoInqireService/getParmacyFullDown")
	Mono<String> getPharmacies(@RequestParam("serviceKey") String serviceKey,
		@RequestParam("pageNo") int pageNo, @RequestParam("numOfRows") int numOfRows);

	@GetMapping("/ErmctInsttInfoInqireService/getParmacyListInfoInqire")
	Mono<String> getPharmaciesByCityAndProvinceOrderBy(@RequestParam("serviceKey") String serviceKey,
		@RequestParam("Q0") String city, @RequestParam("Q1") String province,
		@RequestParam("ORD") String order, @RequestParam("pageNo") int pageNo,
		@RequestParam("numOfRows") int numOfRows);
}
