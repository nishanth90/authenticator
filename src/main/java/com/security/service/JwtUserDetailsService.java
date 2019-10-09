package com.security.service;

import static com.security.constants.SecurityConstants.HTTP_500;
import static com.security.constants.SecurityConstants.INVALID_CREDENTIALS;
import static com.security.constants.SecurityConstants.MONGO_SOCKET_EXCPETION;
import static com.security.constants.SecurityConstants.MONGO_DB_EXCPETION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.mongodb.MongoSocketException;
import com.security.config.JwtTokenUtil;
import com.security.model.JwtRequest;
import com.security.model.JwtResponse;
import com.security.model.User;
import com.security.repository.UserRepository;


/**
 * Service class to validate the credentials from mongodb and generate the JWT token
 * */
@Service
public class JwtUserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public JwtUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ResponseEntity<?> loadUserByUsername(JwtRequest request) throws MongoException, MongoSocketException{
		try {
		User user = userRepository.findByUserName(request.getUsername());
		if (user != null && request.getPassword().equals(user.getPassword())) {
			final String token = jwtTokenUtil.generateToken(request);
			return ResponseEntity.ok(new JwtResponse(token));
		} else {
			return ResponseEntity.badRequest().body(INVALID_CREDENTIALS);
		}
		} catch(MongoSocketException me) {
			return ResponseEntity.status(HTTP_500).body(MONGO_SOCKET_EXCPETION);
		} catch(MongoException me) {
			return ResponseEntity.status(HTTP_500).body(MONGO_DB_EXCPETION);
		}
	}

}
