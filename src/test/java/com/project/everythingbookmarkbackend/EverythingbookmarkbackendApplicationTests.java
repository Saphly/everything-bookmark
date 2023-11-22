package com.project.everythingbookmarkbackend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EverythingbookmarkbackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void clearCollection() {
		TestMongoConfig.clearCollection();
	}

	@Nested
	@DisplayName("GET bookmarks tests")
	class GetAll {

		@Test
		@DisplayName("Should return OK Http Status code - regardless of how many bookmarks are found")
		void shouldReturnOKHttpStatusCode() throws Exception {
			mockMvc.perform(get("/bookmarks")).andExpect(status().isOk());
		}
	}

}
