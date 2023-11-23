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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.project.everythingbookmarkbackend.helpers.JsonFileReader.fileToObjectList;
import static org.hamcrest.Matchers.hasSize;
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

	private String requestBody;

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
					.andExpect(jsonPath("$", hasSize(3)));
		}
	}

	@Nested
	@DisplayName("When bookmarks collection is empty")
	class NoBookmarksFound {

		@Test
		@DisplayName("Should return an ArrayList with size 0")
		public void shouldReturnArrayListWithSize0() throws Exception {
			mockMvc.perform(get("/bookmarks"))
					.andExpect(jsonPath("$", hasSize(0)));
		}
	}


	@Nested
	@DisplayName("Integrated POST Request Tests")
	class PostRequestTests {

		@Nested
		@DisplayName("New valid bookmark tests")
		class NewValidBookmarkTests {
			@BeforeEach
			public void createRequestBody() {
				requestBody = "{\"tag\": \"" + bookmarks.get(0).getTag() +
						"\", \"title\": \"" + bookmarks.get(0).getTitle() +
						"\",\"rating\": \"" + bookmarks.get(0).getRating() +
						"\",\"dateAdded\": \"" + bookmarks.get(0).getDateAdded() +
						"\"}";
			}

			@Test
			@DisplayName("Should return status code 201 upon submitting a valid bookmark")
			public void shouldReturn201StatusWhenSubmitValidBookmark() throws Exception {
				mockMvc.perform(MockMvcRequestBuilders
						.post("/bookmarks")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated());
			}

			@Test
			@DisplayName("Should return the valid bookmark that was submitted")
			public void shouldReturnValidBookmarkSubmitted() throws Exception {
				mockMvc.perform(MockMvcRequestBuilders
						.post("/bookmarks")
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpectAll(
										jsonPath("$.title").value(bookmarks.get(0).getTitle()),
										jsonPath("$.rating").value(bookmarks.get(0).getRating()),
										jsonPath("$.dateAdded").value(bookmarks.get(0).getDateAdded())
								);
			}
		}
	}

	@Nested
	@DisplayName("Invalid new bookmark tests")
	class InvalidNewBookmarkTests {

		@Test
		@DisplayName("Should return a 400 status when no title is provided")
		public void shouldReturn400StatusWhenNoTitle() throws Exception {
			requestBody = "{\"tag\": \"" + bookmarks.get(0).getTag() +
					"\",\"rating\": \"" + bookmarks.get(0).getRating() +
					"\",\"dateAdded\": \"" + bookmarks.get(0).getDateAdded() +
					"\"}";
			mockMvc.perform(MockMvcRequestBuilders
					.post("/bookmarks")
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("Should return a 400 status when no tag is provided")
		public void shouldReturn400StatusWhenNoTag() throws Exception {
			requestBody = "{\"title\": \"" + bookmarks.get(0).getTitle() +
					"\",\"rating\": \"" + bookmarks.get(0).getRating() +
					"\",\"dateAdded\": \"" + bookmarks.get(0).getDateAdded() +
					"\"}";
			mockMvc.perform(MockMvcRequestBuilders
					.post("/bookmarks")
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("Should return a 400 status when rating is more than max value of 5")
		public void shouldReturn400StatusWhenRatingIsOverMaxValue() throws Exception {
			requestBody = "{\"tag\": \"" + bookmarks.get(0).getTag() +
					"\", \"title\": \"" + bookmarks.get(0).getTitle() +
					"\",\"rating\": \"" + 60 +
					"\",\"dateAdded\": \"" + bookmarks.get(0).getDateAdded() +
					"\"}";
			mockMvc.perform(MockMvcRequestBuilders
					.post("/bookmarks")
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}

		@Test
		@DisplayName("Should return a 400 status when rating is less than min value of 1")
		public void shouldReturn400StatusWhenRatingIsUnderMinValue() throws Exception {
			requestBody = "{\"tag\": \"" + bookmarks.get(0).getTag() +
					"\", \"title\": \"" + bookmarks.get(0).getTitle() +
					"\",\"rating\": \"" + -5 +
					"\",\"dateAdded\": \"" + bookmarks.get(0).getDateAdded() +
					"\"}";
			mockMvc.perform(MockMvcRequestBuilders
					.post("/bookmarks")
					.content(requestBody)
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest());
		}
	}

	@Nested
	@DisplayName("Integrated PUT Request tests")
	class PutRequestTests {

		@BeforeEach
		public void repopulateCollection() {
			TestMongoConfig.repopulateCollection(bookmarks);
		}

		@Nested
		@DisplayName("Valid updated bookmark tests")
		class ValidUpdatedBookmarkTests {

			@BeforeEach
			public void updateValidBookmark() {
				testBookmark.set_id(bookmarks.get(0).get_id());
				testBookmark.setTag(bookmarks.get(0).getTag());
				testBookmark.setTitle(bookmarks.get(0).getTitle());
				testBookmark.setRating(bookmarks.get(0).getRating());
				testBookmark.setDateAdded(bookmarks.get(0).getDateAdded());
				testBookmark.setDateStarted(bookmarks.get(2).getDateStarted());
			}

			@BeforeEach
			public void createRequestBody() {
				requestBody = "{\"tag\": \"" + testBookmark.getTag() +
						"\", \"_id\": \"" + testBookmark.get_id() +
						"\", \"title\": \"" + testBookmark.getTitle() +
						"\",\"rating\": \"" + testBookmark.getRating() +
						"\",\"dateAdded\": \"" + testBookmark.getDateAdded() +
						"\",\"dateStarted\": \"" + testBookmark.getDateStarted() +
						"\"}";
			}

			@Test
			@DisplayName("Should return status 200 when a valid bookmark is submitted")
			public void shouldReturnStatus200WhenValidBookmarkSubmitted() throws Exception {
				mockMvc.perform(MockMvcRequestBuilders
						.put("/bookmarks/" + testBookmark.get_id())
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk());
			}

			@Test
			@DisplayName("Should return updated valid bookmark")
			public void shouldReturnValidUpdatedBookmark() throws Exception {
				mockMvc.perform(MockMvcRequestBuilders
						.put("/bookmarks/" + testBookmark.get_id())
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpectAll(jsonPath("$.dateStarted").value(testBookmark.getDateStarted()),
								jsonPath("$._id").value(testBookmark.get_id()));
			}
		}

		@Nested
		@DisplayName("Invalid update bookmark tests")
		class InvalidUpdateBookmarkTests {

			@Test
			@DisplayName("Should return a 404 if the supplied id cannot be found")
			public void shouldReturn404IfIdNotFound() throws Exception {
				testBookmark.set_id("test");
				testBookmark.setTag(bookmarks.get(0).getTag());
				testBookmark.setTitle(bookmarks.get(0).getTitle());
				testBookmark.setDateAdded(bookmarks.get(0).getDateAdded());
				testBookmark.setRating(bookmarks.get(0).getRating());

				requestBody = "{\"tag\": \"" + testBookmark.getTag() +
						"\", \"_id\": \"" + testBookmark.get_id() +
						"\", \"title\": \"" + testBookmark.getTitle() +
						"\",\"rating\": \"" + testBookmark.getRating() +
						"\",\"dateAdded\": \"" + testBookmark.getDateAdded() +
						"\"}";

				mockMvc.perform(MockMvcRequestBuilders
						.put("/bookmarks/" + testBookmark.get_id())
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound());
			}

			@Test
			@DisplayName("Should return a 400 if the bookmark has no title")
			public void shouldReturn400IfNoTitle() throws Exception {
				testBookmark.set_id(testBookmark.get_id());
				testBookmark.setTag(bookmarks.get(0).getTag());
				testBookmark.setTitle("");
				testBookmark.setDateAdded(bookmarks.get(0).getDateAdded());
				testBookmark.setRating(bookmarks.get(0).getRating());

				requestBody = "{\"tag\": \"" + testBookmark.getTag() +
						"\", \"_id\": \"" + testBookmark.get_id() +
						"\", \"title\": \"" + testBookmark.getTitle() +
						"\",\"rating\": \"" + testBookmark.getRating() +
						"\",\"dateAdded\": \"" + testBookmark.getDateAdded() +
						"\"}";

				mockMvc.perform(MockMvcRequestBuilders
						.put("/bookmarks/" + testBookmark.get_id())
						.content(requestBody)
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest());
			}
		}
	}
}
