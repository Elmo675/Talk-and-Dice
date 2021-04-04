package com.github.elmo675;

import com.github.elmo675.DTO.Request.SessionRequest;
import com.github.elmo675.DTO.Response.SessionResponse;
import com.github.elmo675.model.Accessibility;

import com.github.elmo675.model.Session;

import org.aspectj.weaver.ast.Not;
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

import java.sql.Array;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

		String prefix = "/api/v1/entry";
		ResponseEntity<List> response = restTemplate.getForEntity(getRootUrl()+ prefix,List.class);
		//Check if there is a response from the server
		Assert.notNull(response,"There is no response from the server");
		//Check if exit code is correct
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK),"Bad Status Code: " + response.getStatusCode());
		System.out.println(response.getBody());

	}

	@Test
	public void testGetSessionById() {
		int id = 1;
		String prefix = "/api/v1/entry/";
		ResponseEntity<SessionResponse> sessionResponse = restTemplate.getForEntity(getRootUrl()+ prefix + id, SessionResponse.class);
		//Check if Response exist
		Assert.notNull(sessionResponse,"The response is null, there is an error in communication");
		//Check Connection if has 200 exit code
		Assert.isTrue(sessionResponse.getStatusCode().equals(HttpStatus.OK),"Bad Status Code: " + sessionResponse.getStatusCode());
		//Check if any value not-nullable is null
		Assert.notNull(sessionResponse.getBody().getAccess(),"Access is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getContent(),"Content is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getCreatedBy(),"CreatedBy is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getUpdatedBy(),"UpdatedBy is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getCreatedAt(),"CreatedAt is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getUpdatedAt(),"UpdatedAt is incorrect, it has null value");
		//Check if Value for 1st object is correct
		Assert.isTrue(sessionResponse.getBody().getAccess().equals(Accessibility.PRIVATE),"Access is incorrect, it should have value : PRIVATE");
		Assert.isTrue(sessionResponse.getBody().getContent().equals("content"),"Content is incorrect, it should have value: content");
		Assert.isTrue(sessionResponse.getBody().getCreatedBy().equals("ME"),"CreatedBy is incorrect, it should have value: ME");
		Assert.isTrue(sessionResponse.getBody().getUpdatedBy().equals("ME2"),"UpdatedBy is incorrect, it should have value: ME2");
		Assert.isTrue(sessionResponse.getBody().getCreatedAt().toString().equals("Thu Apr 01 20:24:15 CEST 2021"),"CreatedAt is incorrect, its toString() function should have value: Thu Apr 01 20:24:15 CEST 2021, but it has value" +sessionResponse.getBody().getCreatedAt().toString() );
		Assert.isTrue(sessionResponse.getBody().getUpdatedAt().toString().equals("Thu Apr 01 20:24:15 CEST 2021"),"UpdatedAt is incorrect, its toString() function should have value: Thu Apr 01 20:24:15 CEST 2021, but it has value"+ sessionResponse.getBody().getCreatedAt().toString());
	}

	@Test
	public void testCreateSession() {
		SessionRequest request = SessionRequest.builder().build().withAccess(Accessibility.PRIVATE).withContent("TEXT_TEST").withAuthor("ME_EM");
		ResponseEntity<SessionResponse> sessionResponse = restTemplate.postForEntity(getRootUrl() + "api/v1/entry", request, SessionResponse.class);
		//Check if there is an Response from the server
		Assert.notNull(sessionResponse,"The response is null, there is an error in communication");
		//Check Connection if has 200 code
		Assert.isTrue(sessionResponse.getStatusCode().equals(HttpStatus.OK),"Bad Status Code: " + sessionResponse.getStatusCode());
		//Check if any response  value not-nullable is null
		Assert.notNull(sessionResponse.getBody().getAccess(),"Access is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getContent(),"Content is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getCreatedBy(),"CreatedBy is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getUpdatedBy(),"UpdatedBy is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getCreatedAt(),"CreatedAt is incorrect, it has null value");
		Assert.notNull(sessionResponse.getBody().getUpdatedAt(),"UpdatedAt is incorrect, it has null value");
		//Check if response value is the same as in creation
		Assert.isTrue(sessionResponse.getBody().getAccess().equals(Accessibility.PRIVATE),"The Access is incorrect, it should be PRIVATE");
		Assert.isTrue(sessionResponse.getBody().getContent().equals("TEXT_TEST"),"The Access is incorrect, it should be PRIVATE");
		Assert.isTrue(sessionResponse.getBody().getCreatedBy().equals("ME_EM"),"The Access is incorrect, it should be PRIVATE");
		Assert.isTrue(sessionResponse.getBody().getUpdatedBy().equals("ME_EM"),"The Access is incorrect, it should be PRIVATE");
		//Check if new value exist when we use get method on given id
		long id = sessionResponse.getBody().getId();
		SessionResponse get_response = restTemplate.getForObject(getRootUrl() + "api/v1/entry/" + id,SessionResponse.class);
		Assert.isTrue(sessionResponse.getBody().equals(get_response), "Created element isn't the same when get method is used on created id");
	}

	@Test
	public void testUpdateSession() {
		int id = 2;
		String prefix = "api/v1/entry/";
		SessionRequest  request  = SessionRequest.builder().build().withAuthor("Editor").withAccess(Accessibility.FRIENDS).withContent("No content here");
		restTemplate.put(getRootUrl() + prefix + id, request);

		ResponseEntity<SessionResponse> response = restTemplate.getForEntity(getRootUrl() + prefix + id, SessionResponse.class);
		//SessionResponse response = restTemplate.getForObject(getRootUrl() + prefix + id, SessionResponse.class);
		//Check if there is an response
		Assert.notNull(response,"The response is null, there is an error in communication");
		//Check if exit code is correct
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK),"Bad Status Code: " + response.getStatusCode());
		//Check if any response  value not-nullable is null
		Assert.notNull(response.getBody().getAccess(),"Access is incorrect, it has null value");
		Assert.notNull(response.getBody().getContent(),"Content is incorrect, it has null value");
		Assert.notNull(response.getBody().getCreatedBy(),"CreatedBy is incorrect, it has null value");
		Assert.notNull(response.getBody().getUpdatedBy(),"UpdatedBy is incorrect, it has null value");
		Assert.notNull(response.getBody().getCreatedAt(),"CreatedAt is incorrect, it has null value");
		Assert.notNull(response.getBody().getUpdatedAt(),"UpdatedAt is incorrect, it has null value");
		//Check if changed Values are correct
		Assert.isTrue(response.getBody().getUpdatedBy().equals("Editor"),"UpdatedBy is incorrect, it should be changed to Editor");
		Assert.isTrue(response.getBody().getAccess().equals(Accessibility.FRIENDS),"Access is incorrect, it should be changed to FRIENDS");
		Assert.isTrue(response.getBody().getContent().equals("No content here"),"Content is incorrect, it should be changed to No content here");
		Assert.isTrue(!response.getBody().getUpdatedAt().equals(response.getBody().getCreatedAt()),"CreatedAt and UpdatedAt shouldn't be the same value after updating");
	}


	@Test
	public void testDeleteSession() {
		String prefix = "api/v1/entry/";
		int id = 3;
		ResponseEntity<SessionResponse> response = restTemplate.getForEntity(getRootUrl() + prefix+ id , SessionResponse.class);
		//Check if there is a response
		Assert.notNull(response,"The response is null, there is an error in communication");
		//Check if exit code is correct
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.OK),"There is wrong exit code of element with prefix " + prefix + id+ ", Exit Code :" + response.getStatusCode() );
		//Delete the element
		restTemplate.delete(getRootUrl() + "api/v1/entry/" + id);
		//Check if there is no element after deleting
		response = restTemplate.getForEntity(getRootUrl() + prefix + id , SessionResponse.class);
		Assert.notNull(response,"The response after deleting is null, there is an error in communication");
		Assert.isTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND), "The element should be not found but there is different exit code. Exit Code: " + response.getStatusCode());
	}
}
