package me.nexters.doctor24.publicdata.invoker;

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

	@GetMapping("/HsptlAsembySearchService/getHsptlBassInfoInqire")
	Mono<String> getHospitalDetails(@RequestParam("serviceKey") String serviceKey,
                                    @RequestParam("HPID") String hpid);

	@GetMapping("/HsptlAsembySearchService/getHsptlMdcncListInfoInqire")
	Mono<String> getHospitalsByCityAndProvinceOrderBy(@RequestParam("serviceKey") String serviceKey,
                                                      @RequestParam("Q0") String city, @RequestParam("Q1") String province,
                                                      @RequestParam("ORD") String order, @RequestParam("pageNo") int pageNo,
                                                      @RequestParam("numOfRows") int numOfRows);
}
