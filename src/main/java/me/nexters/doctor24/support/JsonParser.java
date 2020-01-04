package me.nexters.doctor24.support;

import lombok.experimental.UtilityClass;
import org.json.JSONObject;
import org.json.XML;

@UtilityClass
public class JsonParser {
    public static JSONObject parse(String xml) {
        return XML.toJSONObject(xml);
    }
}
