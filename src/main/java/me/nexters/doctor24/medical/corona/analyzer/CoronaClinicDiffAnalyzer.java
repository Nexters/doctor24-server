package me.nexters.doctor24.medical.corona.analyzer;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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

		return html.map(Jsoup::parse)
			.flatMapMany(doc -> Flux.fromIterable(doc.getElementsByClass("tb_center").select("tr"))
				.map(element -> element.select("td").get(2).text().replace("*(검체채취 가능)", ""))
				.map(String::trim))
			.flatMap(name -> {
				if (notExist(name)) {
					return Flux.just(NewCoronaClinic.of(name));
				}
				return Flux.empty();
			});
	}

	private boolean notExist(String clinic) {
		CompletableFuture<CoronaHospital> future = CompletableFuture.supplyAsync(
			() -> coronaRepository.findByName(clinic).blockFirst(), inquiryPool);

		return Objects.isNull(future.join());
	}
}
