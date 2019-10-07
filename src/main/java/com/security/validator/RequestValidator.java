package com.security.validator;

import org.springframework.stereotype.Component;

import com.security.model.JwtRequest;

@Component
public class RequestValidator {
	
	public boolean validateRequest(JwtRequest request) {		
		if(request == null ) {
			return false;
		} else if(request.getUsername() == null) { 
			return false;
		} else if(request.getPassword() == null) {
			return false;
		}
		return true;
	}
}
