package me.nexters.doctor24.medical.external.holiday.invoker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
@ReactiveFeignClient(name = "holiday", url = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService")
public interface HolidayInvoker {
    @GetMapping("/getHoliDeInfo")
    Mono<String> getHoliday(@RequestParam("serviceKey") String serviceKey, @RequestParam("solYear") int year);
}

