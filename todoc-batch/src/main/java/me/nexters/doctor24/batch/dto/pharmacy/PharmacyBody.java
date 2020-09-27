package me.nexters.doctor24.batch.dto.pharmacy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
public class PharmacyBody {
	private int pageNo;
	@JsonDeserialize(using = PharmacyDataDeserializer.class)
	private PharmacyData items;

	@Slf4j
	public static class PharmacyDataDeserializer extends JsonDeserializer<PharmacyData> {
		@Override
		public PharmacyData deserialize(
			JsonParser parser, DeserializationContext context) {
			try {
				if (parser.isExpectedStartObjectToken()) {
					return context.readValue(parser, PharmacyData.class);
				}
				if (!parser.hasTextCharacters() || StringUtils.isBlank(parser.getText())) {
					return null;
				}
				ObjectMapper mapper = (ObjectMapper)parser.getCodec();
				return mapper.readValue(parser.getText(), PharmacyData.class);
			} catch (Exception e) {
				log.error("약국 데이터 파싱 failed parsing 'items'", e);
				return null;
			}
		}
	}
}
