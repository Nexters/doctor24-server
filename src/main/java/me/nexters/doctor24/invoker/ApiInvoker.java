package me.nexters.doctor24.invoker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiInvoker {
    private static final String END_POINT = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire";

    private final RestTemplate restTemplate;

    @Value("${data.key}")
    private String key;

    // TODO
    public String getResult(final String city, final String province) throws URISyntaxException {
        URI uri = createUrl(city, province);
        return restTemplate.getForObject(uri, String.class);
    }

    // TODO
    private URI createUrl(final String city, String province) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("serviceKey", key);
        params.add("Q0", city);
        params.add("Q1", province);
        params.add("QZ", "B");
        params.add("QD", "D001");
        params.add("ORD", "NAME");
        params.add("pageNo", "1");
        params.add("numOfRows", "10");
        return UriComponentsBuilder.fromHttpUrl(END_POINT)
                .queryParams(params)
                .build()
                .toUri();
    }
}
