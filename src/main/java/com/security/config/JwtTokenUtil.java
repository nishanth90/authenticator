package com.security.config;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.security.model.JwtRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * Util class to generate and validate the JWT tokens
 * */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60;

	@Value("${jwt.secret}")
	private String secret;
	
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
	 * Subject is the username + password of the requestor
	 * SecretKey is Base64 encoded
	 * */
	private String doGenerateToken(Map<String, Object> claims, JwtRequest subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject.getUsername()+"/"+ subject.getPassword()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000)).signWith(SignatureAlgorithm.HS512, getSecret()).compact();
	}

}
