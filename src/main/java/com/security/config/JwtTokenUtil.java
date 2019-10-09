package com.security.config;

import java.io.Serializable;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.security.model.JwtRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * Util class to generate and validate the JWT token
 * */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.token.expiry}")
	private int expiry;
	
	@Value("${jwt.token.expiry.months}")
	private boolean expiryMonths;
	
	@Value("${jwt.token.expiry.years}")
	private boolean expiryYears;
	
	/*
	 * Method to decode the encoded secret from applicaiton.properties
	 * */
	private String getSecret() {
		return new String(Base64.getDecoder().decode(secret));
	}
	
	/*
	 * Method to call doGenerateToken along with Claims
	 * Claims can be added in future
	 * */
	public String generateToken(JwtRequest request) {
		//New claims can be added if needed in future
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, request);
	}
	
	/*
	 * Method to generate Token based on secret and username
	 * Subject is the username + password of the requester
	 * SecretKey is Base64 encoded
	 * */
	private String doGenerateToken(Map<String, Object> claims, JwtRequest subject) {
		
		//Set by default expiry time to one year
		Calendar today = Calendar.getInstance(); 
		Calendar nextYearToday = today;
		
		//Set the expiry time unit
		if(expiryMonths) {
			nextYearToday.add(Calendar.MONTH, expiry);
		} else if(expiryYears) {
			nextYearToday.add(Calendar.YEAR, expiry);
		} else {
			nextYearToday.add(Calendar.YEAR, 1);
		}
				
		return Jwts.builder().setClaims(claims).setSubject(subject.getUsername()+"/"+ subject.getPassword()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(nextYearToday.getTime()).signWith(SignatureAlgorithm.HS512, getSecret()).compact();
	}

}
