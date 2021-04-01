package com.github.elmo675;

import com.github.elmo675.model.Accessibility;

import com.github.elmo675.model.Session;

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
	public void testGetAllSessions() {

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "api/v1/entry",
				HttpMethod.GET, entity, String.class);
		Assert.notNull(response.getBody());
		System.out.println(response.getBody());
	}

	@Test
	public void testGetSessionById() {
		Session session = restTemplate.getForObject(getRootUrl() + "api/v1/entry/1", Session.class);
		System.out.println(session.getId());
		Assert.notNull(session);
	}

	@Test
	public void testCreateSession() {
		Session session = new Session();
		session.setAccess(Accessibility.PRIVATE);
		session.setContent("TEXT");
		session.setCreatedBy("ME");
		session.setUpdatedBy("ME2");

		ResponseEntity<Session> SessionResponse = restTemplate.postForEntity(getRootUrl() + "api/v1/entry", session, Session.class);
		Assert.notNull(SessionResponse);
		Assert.notNull(SessionResponse.getBody());
		System.out.println(SessionResponse);
	}

	@Test
	public void testUpdateSession() {
		int id = 1;
		Session session = restTemplate.getForObject(getRootUrl() + "api/v1/entry/" + id, Session.class);
		session.setContent("SOME CONTENT HERE");
		session.setUpdatedBy("RANDOM MEN ");

		restTemplate.put(getRootUrl() + "api/v1/entry/" + id, session);

		Session updatedSession = restTemplate.getForObject(getRootUrl() + "api/v1/entry/" + id, Session.class);
		Assert.notNull(updatedSession);
	}

	@Test
	public void testDeleteSession() {
		int id = 2;
		Session session = restTemplate.getForObject(getRootUrl() + "api/v1/entry/" + id, Session.class);
		Assert.notNull(session);


		restTemplate.delete(getRootUrl() + "api/v1/entry/" + id);

		try {
			session = restTemplate.getForObject(getRootUrl() + "api/v1/entry/" + id, Session.class);
		} catch (final HttpClientErrorException e) {
			Assert.isTrue(e.getStatusCode() == HttpStatus.NOT_FOUND);
		}
	}
}
