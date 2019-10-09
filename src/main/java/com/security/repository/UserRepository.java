package com.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoException;
import com.security.model.User;

/**
 * Spring Mongo DB Repository class
 * */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	//This method finds the data using the 'userName' field of the mongo user document
	public User findByUserName(String name) throws MongoException;
	
}