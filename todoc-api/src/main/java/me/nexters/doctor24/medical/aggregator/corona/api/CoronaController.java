package me.nexters.doctor24.medical.aggregator.corona.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.aggregator.corona.NewCoronaClinic;
import me.nexters.doctor24.medical.aggregator.corona.analyzer.CoronaClinicDiffAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author manki.kim
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/v1/corona")
public class CoronaController {

	private final CoronaClinicDiffAnalyzer coronaClinicDiffAnalyzer;

	@GetMapping("/diff")
	public Flux<NewCoronaClinic> getNewClinics() {
		return coronaClinicDiffAnalyzer.getNewClinics();
	}
}
