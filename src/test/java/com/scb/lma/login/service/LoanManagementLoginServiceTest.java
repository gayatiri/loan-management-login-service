package com.scb.lma.login.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.lma.login.model.UserDetails;
import com.scb.lma.login.repository.UserDetailsRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoanManagementLoginServiceTest {

	@InjectMocks
	LoanManagementLoginService loanManagementLoginService;

	@Mock
	UserDetailsRepository userDetailsRepository;

	UserDetails userDetails;
	
	private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
	
	private static final String USER_DETAILS = "src/test/java/data/userDetailsRequest.json";
	

	@Before
	public void init() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		userDetails = mapper.readValue(new File(USER_DETAILS), UserDetails.class);
	}

	@Test
	public void getLoginStatus_ACCEPTED() {
		Mockito.when(userDetailsRepository.findByuserNameAnduserPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(userDetails);
		ResponseEntity<?> response = loanManagementLoginService.check(userDetails);
		assertEquals(userDetails, response.getBody());
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
	}

	@Test
	public void getLoginStatus_FORBIDDEN() {
		Mockito.when(userDetailsRepository.findByuserNameAnduserPassword(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(null);
		ResponseEntity<?> response = loanManagementLoginService.check(userDetails);
		assertEquals("Invalid User Credentails", response.getBody());
		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	

}
