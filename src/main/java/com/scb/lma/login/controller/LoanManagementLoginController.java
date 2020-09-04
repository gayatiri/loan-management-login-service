package com.scb.lma.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scb.lma.login.model.UserDetails;
import com.scb.lma.login.repository.UserDetailsRepository;
import com.scb.lma.login.service.LoanManagementLoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoanManagementLoginController {

	@Autowired
	private LoanManagementLoginService lmaLoginService;

	@Autowired
	UserDetailsRepository UserDetailsRepository;

	@GetMapping("/login")
	public ResponseEntity<?> isValidLogin(@RequestBody UserDetails userDetails) {
		log.info("Request received to validate login credentials");
		return lmaLoginService.check(userDetails);
	}
}