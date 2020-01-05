package me.nexters.doctor24.external.publicdata.invoker;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author manki.kim
 */
@FeignClient(name = "hospital", url = "http://apis.data.go.kr/B552657")
public interface HospitalInvoker {
	@GetMapping("/HsptlAsembySearchService/getHsptlMdcncFullDown")
	String getHospitals(@RequestParam String serviceKey,
		@RequestParam int pageNo, @RequestParam int numOfRows);
}
