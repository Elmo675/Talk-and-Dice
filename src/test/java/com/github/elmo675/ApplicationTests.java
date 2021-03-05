package com.github.elmo675;

import com.github.elmo675.model.Accessibility;
import com.github.elmo675.model.DiaryEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllDiaryEntries() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/entry",
				HttpMethod.GET, entity, String.class);
		Assert.notNull(response.getBody());
	}

	@Test
	public void testGetDiaryEntryById() {
		DiaryEntry diary = restTemplate.getForObject(getRootUrl() + "/entry/1", DiaryEntry.class);
		System.out.println(diary.getCreatedBy());
		Assert.notNull(diary);
	}

	@Test
	public void testCreateDiaryEntry() {
		DiaryEntry diary = new DiaryEntry();
		diary.setAcces(Accessibility.PRIVATE);
		diary.setContent("TEXT");
		diary.setCreatedBy("ME");
		diary.setUpdatedBy("ME2");

		ResponseEntity<DiaryEntry> DiaryEntryResponse = restTemplate.postForEntity(getRootUrl() + "/entry", diary, DiaryEntry.class);
		Assert.notNull(DiaryEntryResponse);
		Assert.notNull(DiaryEntryResponse.getBody());
		System.out.println(DiaryEntryResponse);
	}

	@Test
	public void testUpdateDiaryEntry() {
		int id = 1;
		DiaryEntry diary = restTemplate.getForObject(getRootUrl() + "/entry/" + id, DiaryEntry.class);
		diary.setContent("SOME CONTENT HERE");
		diary.setUpdatedBy("RANDOM MEN ");

		restTemplate.put(getRootUrl() + "/entry/" + id, diary);

		DiaryEntry updatedDiary = restTemplate.getForObject(getRootUrl() + "/entry/" + id, DiaryEntry.class);
		Assert.notNull(updatedDiary);
	}

	@Test
	public void testDeleteDiaryEntry() {
		int id = 2;
		DiaryEntry diary = restTemplate.getForObject(getRootUrl() + "/entry/" + id, DiaryEntry.class);
		Assert.notNull(diary);

		restTemplate.delete(getRootUrl() + "/entry/" + id);

		try {
			diary = restTemplate.getForObject(getRootUrl() + "/entry/" + id, DiaryEntry.class);
		} catch (final HttpClientErrorException e) {
			Assert.isTrue(e.getStatusCode() == HttpStatus.NOT_FOUND);
		}
	}
}
