package me.nexters.doctor24.medical.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class MedicalControllerTest {
	private static final String PRODUCT_API = linkTo(MedicalController.class).toString();

	@Autowired
	private MockMvc mockMvc;

	@Test
	void name() {
		System.out.println(PRODUCT_API);
	}

	// TODO 스펙 나오면 정의할 것
	@Test
	void 반경_1km_내의_병원_조회() throws Exception {
		byte[] result = mockMvc.perform(get("/df")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsByteArray();
	}
}