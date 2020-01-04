package me.nexters.doctor24.support;

import org.json.JSONObject;
import org.json.XML;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonParser {
    public static JSONObject parse(String xml) {
        return XML.toJSONObject(xml);
    }
}
