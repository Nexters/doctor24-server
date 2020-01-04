package me.nexters.doctor24.invoker;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.mockito.BDDMockito.given;

@SpringBootTest
class ApiInvokerTest {
    @Mock
    private ApiInvoker apiInvoker;

    @Test
    void 병원_검색_시_도() throws URISyntaxException, IOException, ParseException {
        String city = "서울특별시";
        String province = "강남구";

        JSONObject jsonObject = getJsonObject("testdata/hospital_result.json");
        given(apiInvoker.getResult(city, province)).willReturn(jsonObject.toJSONString());

        String result = apiInvoker.getResult(city, province);

        System.out.println(result);
    }

    private JSONObject getJsonObject(final String path) throws IOException, ParseException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(file);
        return (JSONObject) parser.parse(fileReader);
    }
}