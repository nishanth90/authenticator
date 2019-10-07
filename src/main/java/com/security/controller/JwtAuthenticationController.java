package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.security.model.JwtRequest;
import com.security.service.JwtUserDetailsService;
import com.security.validator.RequestValidator;

import static com.security.constants.SecurityConstants.INVALID_REQUEST;

/*
 * Controller class to configure the REST URLs
 * */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private JwtUserDetailsService userDetailService;
	
	@Autowired
	private RequestValidator requestValidator;
	
	/*
	 * Method to expose the path /authenticate as POST request
	 * Header : Content-Type : application/json
	 * Body : JSON
	 * Format: {
	 * 			userName:"<username>",
	 * 			password:"<password>"
	 *         }
	 * */
	@RequestMapping(value = "/generateToken", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
	    boolean valid = requestValidator.validateRequest(authenticationRequest);
	    if(!valid) {
	    	return ResponseEntity.badRequest().body(INVALID_REQUEST);
	    }
		return userDetailService.loadUserByUsername(authenticationRequest);
	}
}
