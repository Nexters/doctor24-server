package me.nexters.doctor24.medical.aggregator.corona.invoker;

import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
@ReactiveFeignClient(name = "corona", url = "http://www.mohw.go.kr")
public interface CoronaClinicsInvoker {
	@GetMapping("/react/popup_200128.html")
	Mono<String> getClinicPageHtml();
}
