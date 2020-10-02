package me.nexters.doctor24.medical.external.holiday.invoker;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.nexters.doctor24.medical.external.holiday.HolidayInquires;
import me.nexters.doctor24.medical.external.holiday.dto.HolidayRaw;
import me.nexters.doctor24.medical.external.holiday.dto.HolidayResponse;
import me.nexters.doctor24.medical.external.support.JacksonUtils;
import me.nexters.doctor24.medical.external.support.JsonParser;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicDataInvoker implements HolidayInquires {
    private final HolidayInvoker holidayInvoker;

    @Value("${holiday.key}")
    private String holidayKey;

    @Override
    public List<HolidayRaw> getHolidayRaws(int year, int month) {
        String xmlResult = holidayInvoker.getHoliday(holidayKey, year, month).block();
        HolidayResponse response = toObjectFromResponse(xmlResult, HolidayResponse.class);

        return response.getHolidayRaws();
    }

    private <T> T toObjectFromResponse(String xml, Class<T> tClass) {
        JSONObject jsonResult = JsonParser.parse(xml).getJSONObject("response");
        return JacksonUtils.readValue(jsonResult.toString(), tClass)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));
    }
}
