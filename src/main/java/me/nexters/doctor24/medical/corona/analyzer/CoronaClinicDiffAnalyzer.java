package me.nexters.doctor24.medical.corona.analyzer;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.nexters.doctor24.medical.corona.invoker.CoronaClinicsInvoker;
import me.nexters.doctor24.medical.corona.model.NewCoronaClinic;
import me.nexters.doctor24.medical.corona.model.mongo.CoronaHospital;
import me.nexters.doctor24.medical.corona.repository.CoronaRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author manki.kim
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoronaClinicDiffAnalyzer {
	private final CoronaClinicsInvoker coronaClinicsInvoker;
	private final CoronaRepository coronaRepository;
	private final Executor inquiryPool;

	public Flux<NewCoronaClinic> getNewClinics() {
		Mono<String> html = coronaClinicsInvoker.getClinicPageHtml();
		CompletableFuture<Set<String>> existsClinics = CompletableFuture.supplyAsync(
			() -> coronaRepository.findAll().collectList().block().stream()
				.map(CoronaHospital::getName)
				.collect(Collectors.toSet()), inquiryPool);

		Set<String> resultSet = existsClinics.join();
		return html.map(Jsoup::parse)
			.flatMapMany(doc -> Flux.fromIterable(doc.getElementsByClass("tb_center").select("tr"))
				.map(element -> element.select("td").get(2).text().replace("*(검체채취 가능)", ""))
				.map(String::trim))
			.flatMap(name -> {
				if (!resultSet.contains(name)) {
					return Flux.just(NewCoronaClinic.of(name));
				}
				return Flux.empty();
			});
	}
}
