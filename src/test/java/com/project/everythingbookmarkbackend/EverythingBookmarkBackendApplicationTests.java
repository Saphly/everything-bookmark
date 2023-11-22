package com.project.everythingbookmarkbackend;

import com.project.everythingbookmarkbackend.model.Bookmark;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static com.project.everythingbookmarkbackend.helpers.JsonFileReader.fileToObjectList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class EverythingBookmarkBackendApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private List<Bookmark> bookmarks = fileToObjectList();

	private Bookmark testBookmark = new Bookmark();

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

		@Test
		@DisplayName("Should return JSON - regardless of how many bookmarks are found")
		void shouldReturnJson() throws Exception {
			mockMvc.perform(get("/bookmarks"))
					.andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON));
		}
	}

	@Nested
	@DisplayName("When 1 or more bookmarks are found")
	class WhenBookmarksFound {
		@BeforeEach
		void repopulateCollection() {
			TestMongoConfig.repopulateCollection(bookmarks);
		}

		@Test
		@DisplayName("Should return the found bookmarks as JSON with right number of bookmarks")
		public void shouldReturnRightNumberOfBookmarksAsJSON() throws Exception {
			mockMvc.perform(get("/bookmarks"))
					.andExpect(jsonPath("$[0].title", Matchers.is(bookmarks.get(0).getTitle())))
					.andExpect(jsonPath("$[0].rating", Matchers.is(bookmarks.get(0).getRating())));
		}

		@Test
		@DisplayName("Should return a list of length 2")
		public void shouldReturnListOfLength2() throws Exception {
			mockMvc.perform(get("/bookmarks"))
					.andExpect(jsonPath("$", Matchers.hasSize(2)));
		}
	}

}
